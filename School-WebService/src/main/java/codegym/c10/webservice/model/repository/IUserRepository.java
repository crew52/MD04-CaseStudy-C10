package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
