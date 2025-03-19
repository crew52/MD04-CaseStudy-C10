package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
}