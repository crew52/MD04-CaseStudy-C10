package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.ClassesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassService extends IGenerateServiceStudent<ClassesDTO> {
    Page<ClassesDTO> findAll(Pageable pageable); // Thêm để hỗ trợ phân trang
    List<ClassesDTO> findAll(); // Giữ nguyên cho GET không phân trang
    Page<ClassesDTO> findByClassName(String className, Pageable pageable);
}
