package codegym.c10.webservice.service.user;

import codegym.c10.webservice.model.entity.Role;
import codegym.c10.webservice.model.entity.User;
import codegym.c10.webservice.payload.request.LoginRequest;
import codegym.c10.webservice.payload.request.RegisterUserRequest;
import codegym.c10.webservice.payload.respones.ApiResponse;
import codegym.c10.webservice.payload.respones.UserPriciple;
import codegym.c10.webservice.repository.RoleRepository;
import codegym.c10.webservice.repository.UserRepository;
import codegym.c10.webservice.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class UserService implements  IUserService , UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public ApiResponse loginUser(LoginRequest loginRequest) {
        String loginInput = loginRequest.getUsername();
        User user = userRepository.findByUsername(loginInput);
        if (user == null) {
            return new ApiResponse(false, "Invalid username or password");
        }
        if (!passwordEncoder.matches(loginInput, user.getPassword())) {
            return new ApiResponse(false, "Invalid username or password");
        }


        UserDetails userDetails = UserPriciple.build(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", user.getRole());

        String jwtToken = jwtService.generateTokenLogin(extraClaims, userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);

        return new ApiResponse(true, "Login successful", response);
    }

    @Override
    public ApiResponse registerUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.existsByUsername(registerUserRequest.getUsername()))  {
            return new ApiResponse(false, "Username already exists");
        }

        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));


        Role defaultRole = roleRepository.findByName("TEACHER");
        if (defaultRole == null) {
            return new ApiResponse(false, "Invalid role");
        }
        user.setRole(defaultRole);

        userRepository.save(user);

        return new ApiResponse(true, "Teacher registered successfully");


    }

    @Override
    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
User user = userRepository.findByUsername(username);
if (user == null) {
throw new UsernameNotFoundException("Username not found");
}
return UserPriciple.build(user);
    }
}
