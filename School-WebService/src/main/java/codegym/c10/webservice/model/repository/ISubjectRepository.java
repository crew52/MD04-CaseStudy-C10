package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Subject;
import org.springframework.data.repository.CrudRepository;

public interface ISubjectRepository extends CrudRepository<Subject, Integer> {
}
