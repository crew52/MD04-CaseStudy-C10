package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.dto.GradeDTO;
import codegym.c10.webservice.model.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGradeService extends IGenerateService<GradeDTO> {
    Page<GradeDTO> searchGrades(String className, String studentName, String subjectName, String examType, int page, int size);
    Page<GradeDTO> searchGradesByTeacher(Integer teacherId, String className, String studentName, String subjectName, String examType, int page, int size);
    Page<GradeDTO> findAll(Pageable pageable);
}
