package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.ClassesDTO;
import codegym.c10.webservice.model.entity.Classes;
import codegym.c10.webservice.model.eNum.GradeLevel;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassesService implements IClassService {

    @Autowired
    private ClassesRepository classesRepository;

    private ClassesDTO convertToDTO(Classes classes) {
        ClassesDTO dto = new ClassesDTO();
        dto.setId(classes.getId());
        dto.setClassName(classes.getClassName());
        dto.setTeacherId(classes.getTeacher() != null ? classes.getTeacher().getId() : null);
        dto.setTeacherName(classes.getTeacher() != null ? classes.getTeacher().getName() : null);
        dto.setGradeLevel(classes.getGradeLevel().name());
        return dto;
    }

    private Classes convertToEntity(ClassesDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("ClassesDTO cannot be null");
        }
        if (dto.getClassName() == null || dto.getGradeLevel() == null) {
            throw new IllegalArgumentException("className and gradeLevel are required");
        }
        Classes classes = new Classes();
        classes.setId(dto.getId());
        classes.setClassName(dto.getClassName());
        if (dto.getTeacherId() != null) {
            Teacher teacher = new Teacher();
            teacher.setId(dto.getTeacherId());
            classes.setTeacher(teacher);
        } else {
            classes.setTeacher(null);
        }
        try {
            classes.setGradeLevel(GradeLevel.valueOf(dto.getGradeLevel()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gradeLevel: " + dto.getGradeLevel());
        }
        return classes;
    }

    @Override
    public Page<ClassesDTO> findAll(Pageable pageable) {
        return classesRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public List<ClassesDTO> findAll() {
        return classesRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Page<ClassesDTO> findByClassName(String className, Pageable pageable) {
        return classesRepository.findByClassNameContainingIgnoreCase(className, pageable).map(this::convertToDTO);
    }

    @Override
    public ClassesDTO findById(Integer id) {
        return classesRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
    }

    @Override
    public ClassesDTO save(ClassesDTO dto) {
        // Kiểm tra trùng lặp teacher_id
        if (dto.getTeacherId() != null) {
            Optional<Classes> existingClassWithTeacher = classesRepository.findByTeacher_Id(dto.getTeacherId());
            if (existingClassWithTeacher.isPresent()) {
                throw new IllegalArgumentException("Giáo viên với ID " + dto.getTeacherId() + " đã được gán cho lớp " + existingClassWithTeacher.get().getClassName() + "!");
            }
        }

        // Kiểm tra trùng lặp class_name
        if (classesRepository.findByClassName(dto.getClassName()).isPresent()) {
            throw new IllegalArgumentException("Tên lớp " + dto.getClassName() + " đã tồn tại!");
        }

        Classes classes = convertToEntity(dto);
        Classes savedClasses = classesRepository.save(classes);
        return convertToDTO(savedClasses);
    }

    @Override
    public ClassesDTO update(Integer id, ClassesDTO dto) {
        Classes existingClass = classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));

        // Kiểm tra trùng lặp teacher_id (nếu teacher_id thay đổi)
        if (dto.getTeacherId() != null) {
            Optional<Classes> existingClassWithTeacher = classesRepository.findByTeacher_Id(dto.getTeacherId());
            if (existingClassWithTeacher.isPresent() && !existingClassWithTeacher.get().getId().equals(id)) {
                throw new IllegalArgumentException("Giáo viên với ID " + dto.getTeacherId() + " đã được gán cho lớp " + existingClassWithTeacher.get().getClassName() + "!");
            }
        }

        // Kiểm tra trùng lặp class_name (nếu class_name thay đổi)
        if (!dto.getClassName().equals(existingClass.getClassName()) &&
                classesRepository.findByClassName(dto.getClassName()).isPresent()) {
            throw new IllegalArgumentException("Tên lớp " + dto.getClassName() + " đã tồn tại!");
        }

        existingClass.setClassName(dto.getClassName());
        if (dto.getTeacherId() != null) {
            Teacher teacher = new Teacher();
            teacher.setId(dto.getTeacherId());
            existingClass.setTeacher(teacher);
        } else {
            existingClass.setTeacher(null);
        }
        existingClass.setGradeLevel(GradeLevel.valueOf(dto.getGradeLevel()));
        Classes updatedClass = classesRepository.save(existingClass);
        return convertToDTO(updatedClass);
    }

    @Override
    public void delete(Integer id) {
        Classes classes = classesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with id: " + id));
        classesRepository.delete(classes);
    }
}