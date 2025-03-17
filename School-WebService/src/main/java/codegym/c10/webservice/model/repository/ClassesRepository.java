package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {
    Optional<Classes> findByTeacher_Id(Integer teacherId); // Kiểm tra teacher_id
    Optional<Classes> findByClassName(String className);   // Kiểm tra class_name
    Page<Classes> findByClassNameContainingIgnoreCase(String className, Pageable pageable);
}
