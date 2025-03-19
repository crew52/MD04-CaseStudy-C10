package codegym.c10.webservice.model.service.iface;

import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.model.service.IGenerateService;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService extends IGenerateService<User> {
    UserDetails loadUserByUsername(String username);
    User findByUsername(String username);
}
