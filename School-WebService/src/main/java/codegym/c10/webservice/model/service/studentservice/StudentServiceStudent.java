package codegym.c10.webservice.model.service.studentservice;

import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.repository.IStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceStudent implements IStudentServiceStudent {
    @Autowired
    private IStudentRepository iStudentRepository;

    @Override
    public Page<StudentDTO> searchStudents(String name, String className, Pageable pageable) {
        return iStudentRepository.searchStudents(name, className, pageable);
    }

    @Override
    public Page<StudentDTO> findAll(Pageable pageable) {
        return iStudentRepository.findAllStudents(pageable);
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        // Xử lý logic lưu Student (DTO -> Entity) nếu cần
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        iStudentRepository.deleteById(id);
    }

    @Override
    public StudentDTO findById(Integer id) {
        return null;
    }
}
