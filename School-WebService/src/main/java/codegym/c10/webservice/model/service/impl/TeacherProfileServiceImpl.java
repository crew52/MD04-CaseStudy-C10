package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.dto.ClassesDTO;
import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.dto.TeacherProfileDTO;
import codegym.c10.webservice.model.entity.Classes;
import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.repository.ClassesRepository;
import codegym.c10.webservice.model.repository.IStudentRepository;
import codegym.c10.webservice.model.repository.ITeacherProfileRepository;
import codegym.c10.webservice.model.service.iface.ITeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeacherProfileServiceImpl implements ITeacherProfileService {

    @Autowired
    private ITeacherProfileRepository teacherProfileRepository;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Override
    public Iterable<TeacherProfileDTO> findAll() {
        return StreamSupport.stream(teacherProfileRepository.findAll().spliterator(), false)
                .map(this::convertToDTOWithDetails)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherProfileDTO save(TeacherProfileDTO dto) {
        Teacher teacher = convertToEntity(dto);
        Teacher savedTeacher = teacherProfileRepository.save(teacher);
        return convertToDTOWithDetails(savedTeacher);
    }

    @Override
    public Optional<TeacherProfileDTO> findById(Integer id) {
        return teacherProfileRepository.findById(id)
                .map(this::convertToDTOWithDetails);
    }

    @Override
    public void remove(Integer id) {
        teacherProfileRepository.deleteById(id);
    }

    @Override
    public TeacherProfileDTO findTeacherByUserId(Integer userId) {
        Teacher teacher = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user ID: " + userId));
        return convertToDTOWithDetails(teacher);
    }

    @Override
    public TeacherProfileDTO updateTeacherByUserId(Integer userId, TeacherProfileDTO dto) {
        Teacher teacher = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user ID: " + userId));

        teacher.setName(dto.getName());
        teacher.setDob(dto.getDob());
        teacher.setGender(dto.getGender() != null ? Enum.valueOf(codegym.c10.webservice.model.eNum.Gender.class, dto.getGender()) : null);
        teacher.setPhone(dto.getPhone());
        teacher.setEmail(dto.getEmail());

        Teacher updatedTeacher = teacherProfileRepository.save(teacher);
        return convertToDTOWithDetails(updatedTeacher);
    }

    @Override
    public TeacherProfileDTO findTeacherDetailsByUserId(Integer userId) {
        Teacher teacher = teacherProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Teacher not found for user ID: " + userId));
        return convertToDTOWithDetails(teacher);
    }

    // Phương thức chuyển đổi Teacher sang DTO với thông tin chi tiết (lớp và học sinh)
    private TeacherProfileDTO convertToDTOWithDetails(Teacher teacher) {
        TeacherProfileDTO dto = new TeacherProfileDTO();
        dto.setTeacherId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setDob(teacher.getDob());
        dto.setGender(teacher.getGender() != null ? teacher.getGender().name() : null);
        dto.setPhone(teacher.getPhone());
        dto.setEmail(teacher.getEmail());
        dto.setSubjectName(teacher.getSubject() != null ? teacher.getSubject().getSubjectName() : null);
        dto.setUserId(teacher.getUser() != null ? teacher.getUser().getId() : null);
        dto.setUsername(teacher.getUser() != null ? teacher.getUser().getUsername() : null);

        // Lấy thông tin lớp học mà giáo viên chủ nhiệm
        Optional<Classes> classEntity = classesRepository.findByTeacher_Id(teacher.getId());
        if (classEntity.isPresent()) {
            Classes classes = classEntity.get();
            dto.setClassInfo(convertToClassesDTO(classes));

            // Lấy danh sách học sinh trong lớp
            List<Student> students = studentRepository.findByClassEntity_Id(classes.getId());
            if (!students.isEmpty()) {
                dto.setStudents(students.stream()
                        .map(this::convertToStudentDTO)
                        .collect(Collectors.toList()));
            } else {
                dto.setStudents(null); // Nếu không có học sinh, đặt là null
            }
        } else {
            dto.setClassInfo(null); // Nếu không có lớp, đặt là null
            dto.setStudents(null);  // Không có lớp thì không có học sinh
        }

        return dto;
    }

    private ClassesDTO convertToClassesDTO(Classes classEntity) {
        ClassesDTO dto = new ClassesDTO();
        dto.setId(classEntity.getId());
        dto.setClassName(classEntity.getClassName());
        dto.setTeacherId(classEntity.getTeacher() != null ? classEntity.getTeacher().getId() : null);
        dto.setTeacherName(classEntity.getTeacher() != null ? classEntity.getTeacher().getName() : null);
        dto.setGradeLevel(classEntity.getGradeLevel().name());
        return dto;
    }

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setClassId(student.getClassEntity() != null ? student.getClassEntity().getId() : null);
        dto.setClassName(student.getClassEntity() != null ? student.getClassEntity().getClassName() : null);
        dto.setParentContact(student.getParentContact());
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
        // Không gán subject hoặc user ở đây vì DTO không cung cấp đủ thông tin
        return teacher;
    }
}