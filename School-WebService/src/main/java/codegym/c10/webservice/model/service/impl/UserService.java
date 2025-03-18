package codegym.c10.webservice.model.service.impl;

import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.model.repository.IUserRepository;
import codegym.c10.webservice.model.service.iface.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Override
    public User save(User T) {
        return userRepository.save(T);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
