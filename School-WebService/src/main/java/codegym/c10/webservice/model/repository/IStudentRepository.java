package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> { // Sửa Long thành Integer
    List<Student> findByNameContaining(String name);       //  tìm kiếm theo tên
    List<Student> findByClassEntityId(Integer classId);    //  tìm kiếm theo lớp
}