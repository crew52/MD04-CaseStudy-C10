package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.dto.StudentDTO;
import codegym.c10.webservice.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT new codegym.c10.webservice.model.dto.StudentDTO(s.id, s.name, s.dob, s.gender, c.id, c, s.parentContact) " +
            "FROM Student s JOIN s.classEntity c " +
            "WHERE (:name IS NULL OR s.name LIKE %:name%) " +
            "AND (:className IS NULL OR c.className LIKE %:className%)")
    Page<StudentDTO> searchStudents(@Param("name") String name,
                                    @Param("className") String className,
                                    Pageable pageable);

    @Query("SELECT new codegym.c10.webservice.model.dto.StudentDTO(s.id, s.name, s.dob, s.gender, c.id, c, s.parentContact) " +
            "FROM Student s JOIN s.classEntity c")
    Page<StudentDTO> findAllStudents(Pageable pageable);
}