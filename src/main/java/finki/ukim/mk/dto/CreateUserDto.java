package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.User;
import finki.ukim.mk.model.enumerations.Role;

public record CreateUserDto(
        String username,
        String password,
        String repeatPassword,
        String name,
        String surname,
        Role role
) {


    public User toUser() {
        return new User(username, password, name, surname, role);
    }
}

