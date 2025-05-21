package finki.ukim.mk.service.domain;

import finki.ukim.mk.model.domain.User;
import finki.ukim.mk.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);

    User login(String username, String password);

    User findByUsername(String username);

}



