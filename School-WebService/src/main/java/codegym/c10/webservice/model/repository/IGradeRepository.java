package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IGradeRepository extends JpaRepository<Grade, Integer> {
    @Query("SELECT g FROM Grade g WHERE " +
            "(:className IS NULL OR LOWER(g.student.classEntity.className) LIKE LOWER(CONCAT('%', :className, '%'))) " +
            "AND (:studentName IS NULL OR LOWER(g.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))) " +
            "AND (:subjectName IS NULL OR LOWER(g.subject.subjectName) LIKE LOWER(CONCAT('%', :subjectName, '%'))) " +
            "AND (:examType IS NULL OR LOWER(g.examType) LIKE LOWER(CONCAT('%', :examType, '%')))")
    Page<Grade> searchGrades(@Param("className") String className,
                             @Param("studentName") String studentName,
                             @Param("subjectName") String subjectName,
                             @Param("examType") String examType,
                             Pageable pageable);

    @Query("SELECT g FROM Grade g WHERE " +
            "(:className IS NULL OR LOWER(g.student.classEntity.className) LIKE LOWER(CONCAT('%', :className, '%'))) " +
            "AND (:studentName IS NULL OR LOWER(g.student.name) LIKE LOWER(CONCAT('%', :studentName, '%'))) " +
            "AND (:subjectName IS NULL OR LOWER(g.subject.subjectName) LIKE LOWER(CONCAT('%', :subjectName, '%'))) " +
            "AND (:examType IS NULL OR LOWER(g.examType) LIKE LOWER(CONCAT('%', :examType, '%'))) " +
            "AND (:teacherId IS NULL OR g.teacher.id = :teacherId)")
    Page<Grade> searchGradesByTeacher(
            @Param("teacherId") Integer teacherId,
            @Param("className") String className,
            @Param("studentName") String studentName,
            @Param("subjectName") String subjectName,
            @Param("examType") String examType,
            Pageable pageable);

}
