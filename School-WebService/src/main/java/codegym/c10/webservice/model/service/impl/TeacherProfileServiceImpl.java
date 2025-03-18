package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.dto.TeacherProfileDTO;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.repository.ITeacherProfileRepository;
import codegym.c10.webservice.model.service.iface.ITeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeacherProfileServiceImpl implements ITeacherProfileService {

    @Autowired
    private ITeacherProfileRepository teacherProfileRepository;

    @Override
    public Iterable<TeacherProfileDTO> findAll() {
        return StreamSupport.stream(teacherProfileRepository.findAll().spliterator(), false)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherProfileDTO save(TeacherProfileDTO dto) {
        Teacher teacher = convertToEntity(dto);
        Teacher savedTeacher = teacherProfileRepository.save(teacher);
        return convertToDTO(savedTeacher);
    }

    @Override
    public Optional<TeacherProfileDTO> findById(Integer id) {
        return teacherProfileRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public void remove(Integer id) {
        teacherProfileRepository.deleteById(id);
    }

    @Override
    public TeacherProfileDTO findTeacherByUserId(Integer userId) {
        Teacher teacher = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user ID: " + userId));
        return convertToDTO(teacher);
    }

    @Override
    public TeacherProfileDTO updateTeacherByUserId(Integer userId, TeacherProfileDTO dto) {
        Teacher teacher = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user ID: " + userId));

        // Cập nhật thông tin từ DTO
        teacher.setName(dto.getName());
        teacher.setDob(dto.getDob());
        teacher.setGender(dto.getGender() != null ? Enum.valueOf(codegym.c10.webservice.model.eNum.Gender.class, dto.getGender()) : null);
        teacher.setPhone(dto.getPhone());
        teacher.setEmail(dto.getEmail());

        // Lưu lại
        Teacher updatedTeacher = teacherProfileRepository.save(teacher);
        return convertToDTO(updatedTeacher);
    }

    private TeacherProfileDTO convertToDTO(Teacher teacher) {
        TeacherProfileDTO dto = new TeacherProfileDTO();
        dto.setTeacherId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setDob(teacher.getDob());
        dto.setGender(teacher.getGender().name()); // Chuyển enum Gender thành String
        dto.setPhone(teacher.getPhone());
        dto.setEmail(teacher.getEmail());
        dto.setSubjectName(teacher.getSubject() != null ? teacher.getSubject().getSubjectName() : null);
        dto.setUserId(teacher.getUser() != null ? teacher.getUser().getId() : null);
        dto.setUsername(teacher.getUser() != null ? teacher.getUser().getUsername() : null);
        return dto;
    }

    private Teacher convertToEntity(TeacherProfileDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setId(dto.getTeacherId());
        teacher.setName(dto.getName());
        teacher.setDob(dto.getDob());
        teacher.setGender(dto.getGender() != null ? Enum.valueOf(codegym.c10.webservice.model.eNum.Gender.class, dto.getGender()) : null);
        teacher.setPhone(dto.getPhone());
        teacher.setEmail(dto.getEmail());
        // Không cập nhật subject và user ở đây vì chúng không được thay đổi qua DTO này
        return teacher;
    }
}