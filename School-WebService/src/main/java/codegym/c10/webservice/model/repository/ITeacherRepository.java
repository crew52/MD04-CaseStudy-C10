package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.eNum.Gender;
import codegym.c10.webservice.model.eNum.SubjectEnum;
import codegym.c10.webservice.model.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITeacherRepository extends JpaRepository<Teacher, Integer> {
    Optional<Teacher> findByUserId(Integer userId);
    @Query("SELECT t FROM Teacher t WHERE " +
            "(:name IS NULL OR t.name LIKE %:name%) AND " +
            "(:gender IS NULL OR t.gender = :gender) AND " +
            "(:subjectName IS NULL OR t.subject.subjectName = :subjectName)")
    Page<Teacher> searchTeachers(
            @Param("name") String name,
            @Param("gender") Gender gender,
            @Param("subjectName") SubjectEnum subjectName,
            Pageable pageable);

}
