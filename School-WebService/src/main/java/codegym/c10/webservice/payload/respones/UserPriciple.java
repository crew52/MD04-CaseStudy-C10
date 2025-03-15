package codegym.c10.webservice.payload.respones;

import codegym.c10.webservice.model.entity.Role;
import codegym.c10.webservice.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPriciple implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPriciple(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UserPriciple build(User user){
        List<GrantedAuthority> author = new ArrayList<>();
        Role role = user.getRole();
        author.add(new SimpleGrantedAuthority(role.getRoleName().name()));

        return new UserPriciple(user.getUsername(), user.getPassword(), author);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
