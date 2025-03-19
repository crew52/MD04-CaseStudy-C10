package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITeacherProfileRepository extends JpaRepository<Teacher , Integer> {
    Optional<Teacher> findByUserId(Integer userId);
}
