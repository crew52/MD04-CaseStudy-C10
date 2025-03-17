package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.eNum.SubjectEnum;
import codegym.c10.webservice.model.entity.Teacher;
import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.model.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface ITeacherService extends IGenerateService<Teacher> {
    Page<Teacher> findAllPaged(int pageNumber, int pageSize, String sortBy, Sort.Direction direction);
    Optional<Teacher> findByUserId(Integer userId); // Tìm giáo viên theo user_id
    Teacher updateByUserId(Integer userId, Teacher teacher);
    Page<Teacher> searchTeachers(String name, Gender gender, SubjectEnum subjectName, int pageNumber, int pageSize, String sortBy, Sort.Direction direction);
    public Teacher createTeacherWithUser(Teacher teacher, User user);
}
