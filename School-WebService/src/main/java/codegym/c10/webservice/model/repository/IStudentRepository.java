package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByClassEntity_Id(Integer classId);
    Page<Student> findAll(Pageable pageable);

    // Tìm kiếm học sinh theo tên lớp (className) với phân trang
    @Query("SELECT s FROM Student s WHERE s.classEntity.className LIKE %:className%")
    Page<Student> findByClassName(@Param("className") String className, Pageable pageable);

    // Tìm kiếm học sinh theo tên học sinh với phân trang
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
    Page<Student> findByName(@Param("name") String name, Pageable pageable);

    // Tìm kiếm học sinh theo tên lớp và tên học sinh với phân trang
    @Query("SELECT s FROM Student s WHERE s.classEntity.className LIKE %:className% AND s.name LIKE %:name%")
    Page<Student> findByClassNameAndName(@Param("className") String className,
                                         @Param("name") String name,
                                         Pageable pageable);
}