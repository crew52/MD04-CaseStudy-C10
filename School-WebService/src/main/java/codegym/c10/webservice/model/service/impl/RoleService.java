package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.entity.Role;
import codegym.c10.webservice.model.repository.IRoleRepository;
import codegym.c10.webservice.model.service.iface.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepo;

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }


    @Override
    public Role save(Role role) {
        return null;
        // TODO
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void remove(Integer id) {

    }

}
