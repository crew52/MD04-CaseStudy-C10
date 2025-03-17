package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
