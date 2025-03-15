package codegym.c10.webservice.service.user;

import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.payload.request.LoginRequest;
import codegym.c10.webservice.payload.request.RegisterUserRequest;
import codegym.c10.webservice.payload.respones.ApiResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    ApiResponse loginUser(LoginRequest loginRequest);
    ApiResponse registerUser(RegisterUserRequest registerUserRequest);
    User getUser(String username);
}
