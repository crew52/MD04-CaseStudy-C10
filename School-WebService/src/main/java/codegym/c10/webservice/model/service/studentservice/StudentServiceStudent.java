package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.entity.Classes;
import codegym.c10.webservice.model.entity.Student;
import codegym.c10.webservice.model.repository.ClassesRepository;
import codegym.c10.webservice.model.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceStudent implements IStudentServiceStudent {
    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private ClassesRepository classesRepository;

    // Chuyển từ Entity sang DTO
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setDob(student.getDob());
        dto.setGender(student.getGender());
        dto.setParentContact(student.getParentContact());
        if (student.getClassEntity() != null) {
            dto.setClassId(student.getClassEntity().getId());
            dto.setClassName(student.getClassEntity().getClassName());
        }
        return dto;
    }

    // Chuyển từ DTO sang Entity
    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setId(dto.getId());
        student.setName(dto.getName());
        student.setDob(dto.getDob());
        student.setGender(dto.getGender());
        student.setParentContact(dto.getParentContact());

        // Xử lý classEntity dựa trên classId
        if (dto.getClassId() != null) {
            Optional<Classes> classEntity = classesRepository.findById(dto.getClassId());
            classEntity.ifPresent(student::setClassEntity);
        }
        return student;
    }

    @Override
    public Page<StudentDTO> searchByClassName(String className, Pageable pageable) {
        Page<Student> students = studentRepository.findByClassName(className, pageable);
        return students.map(this::convertToDTO);
    }

    @Override
    public Page<StudentDTO> searchByName(String name, Pageable pageable) {
        Page<Student> students = studentRepository.findByName(name, pageable);
        return students.map(this::convertToDTO);
    }

    @Override
    public Page<StudentDTO> searchByClassNameAndName(String className, String name, Pageable pageable) {
        Page<Student> students = studentRepository.findByClassNameAndName(className, name, pageable);
        return students.map(this::convertToDTO);
    }

    @Override
    public Page<StudentDTO> findAll(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(this::convertToDTO);
    }

    @Override
    public StudentDTO findById(Integer id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::convertToDTO).orElse(null);
    }

    @Override
    public StudentDTO save(StudentDTO dto) {
        Student student = convertToEntity(dto);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Override
    public StudentDTO update(Integer id, StudentDTO dto) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(dto.getName());
            student.setDob(dto.getDob());
            student.setGender(dto.getGender());
            student.setParentContact(dto.getParentContact());

            // Cập nhật classEntity nếu classId thay đổi
            if (dto.getClassId() != null) {
                Optional<Classes> classEntity = classesRepository.findById(dto.getClassId());
                classEntity.ifPresent(student::setClassEntity);
            }
            Student updatedStudent = studentRepository.save(student);
            return convertToDTO(updatedStudent);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }
}
