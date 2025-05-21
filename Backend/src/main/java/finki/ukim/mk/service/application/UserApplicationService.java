package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.CreateUserDto;
import finki.ukim.mk.dto.DisplayUserDto;
import finki.ukim.mk.dto.LoginResponseDTO;
import finki.ukim.mk.dto.LoginUserDto;

import java.util.Optional;

public interface UserApplicationService {

    Optional<DisplayUserDto> register(CreateUserDto createUserDto);

    Optional<LoginResponseDTO> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);
}

