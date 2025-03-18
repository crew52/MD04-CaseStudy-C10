package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.eNum.SubjectEnum;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.model.repository.ITeacherRepository;
import codegym.c10.webservice.model.repository.IUserRepository;
import codegym.c10.webservice.model.service.iface.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private ITeacherRepository teacherRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Page<Teacher> findAllPaged(int pageNumber, int pageSize, @Nullable String sortBy, @Nullable Sort.Direction direction) {
        Sort sort = null;
        if (sortBy != null && direction != null) {
            sort = Sort.by(direction, sortBy);
        } else if (sortBy != null) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by("id").ascending(); // Sắp xếp mặc định theo ID tăng dần
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return teacherRepository.findAll(pageable);
    }


    @Override
    public Iterable<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher save(Teacher teacher) {
        if (teacher.getId() != null) {
            Optional<Teacher> existingTeacher = teacherRepository.findById(teacher.getId());
            if (existingTeacher.isPresent()) {
                Teacher current = existingTeacher.get();
                // Chỉ cập nhật các trường được gửi, giữ nguyên user nếu không thay đổi
                current.setName(teacher.getName());
                current.setDob(teacher.getDob());
                current.setGender(teacher.getGender());
                current.setEmail(teacher.getEmail());
                current.setPhone(teacher.getPhone());
                current.setSubject(teacher.getSubject());
                // Giữ nguyên user nếu request không gửi user
                if (teacher.getUser() != null) {
                    current.setUser(teacher.getUser());
                }
                return teacherRepository.save(current);
            }
        }
        return teacherRepository.save(teacher); // Thêm mới nếu không có id
    }

    @Override
    public Optional<Teacher> findById(Integer id) {
        return teacherRepository.findById(id);
    }

    @Override
    public void remove(Integer id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Optional<Teacher> findByUserId(Integer userId) {
        return teacherRepository.findByUserId(userId);
    }

    @Override
    public Teacher updateByUserId(Integer userId, Teacher teacher) {
        Optional<Teacher> existingTeacher = teacherRepository.findByUserId(userId);
        if (existingTeacher.isPresent()) {
            Teacher teacherToUpdate = existingTeacher.get();
            // Cập nhật các trường từ Person
            teacherToUpdate.setName(teacher.getName());
            teacherToUpdate.setDob(teacher.getDob());
            teacherToUpdate.setGender(teacher.getGender());
            // Cập nhật các trường của Teacher
            teacherToUpdate.setPhone(teacher.getPhone());
            teacherToUpdate.setEmail(teacher.getEmail());
            teacherToUpdate.setSubject(teacher.getSubject());
            // Không thay đổi user vì đây là điều kiện tìm kiếm
            return teacherRepository.save(teacherToUpdate);
        } else {
            throw new RuntimeException("Teacher not found with user_id: " + userId);
        }
    }

    @Override
    public Page<Teacher> searchTeachers(String name, Gender gender, SubjectEnum subjectName, int pageNumber, int pageSize, String sortBy, Sort.Direction direction) {
        Sort sort = (sortBy != null && direction != null) ? Sort.by(direction, sortBy) : Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        return teacherRepository.searchTeachers(name, gender, subjectName, pageable);
    }

    @Override
    public Teacher createTeacherWithUser(Teacher teacher, User user) {
        // Kiểm tra username đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username " + user.getUsername() + " already exists");
        }

        // Lưu User mới
        User savedUser = userRepository.save(user);

        // Gắn User vào Teacher
        teacher.setUser(savedUser);

        // Lưu Teacher
        return teacherRepository.save(teacher);
    }
}


