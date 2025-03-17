package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGradeRepository extends JpaRepository<Grade, Integer> {
}
