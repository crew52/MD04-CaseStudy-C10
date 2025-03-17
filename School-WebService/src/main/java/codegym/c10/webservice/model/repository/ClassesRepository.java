package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {
}
