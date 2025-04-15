package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.CreateGuestDto;
import finki.ukim.mk.dto.CreateHostDto;
import finki.ukim.mk.dto.DisplayGuestDto;
import finki.ukim.mk.dto.DisplayHostDto;

import java.util.List;
import java.util.Optional;

public interface GuestApplicationService {
    List<DisplayGuestDto> findAll();

    Optional<DisplayGuestDto> findById(Long id);

    Optional<DisplayGuestDto> save(CreateGuestDto host);

    Optional<DisplayGuestDto> update(Long id, CreateGuestDto host);

    void deleteById(Long id);
}
