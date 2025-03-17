package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStudentServiceStudent extends IGenerateServiceStudent<StudentDTO> {
    Page<StudentDTO> searchByClassName(String className, Pageable pageable);
    Page<StudentDTO> searchByName(String name, Pageable pageable);
    Page<StudentDTO> searchByClassNameAndName(String className, String name, Pageable pageable);
}