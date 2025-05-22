package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.CreateCountryDto;
import finki.ukim.mk.dto.CreateGuestDto;
import finki.ukim.mk.dto.DisplayCountryDto;
import finki.ukim.mk.dto.DisplayGuestDto;

import java.util.List;
import java.util.Optional;

public interface CountryApplicationService {

    List<DisplayCountryDto> findAll();

    Optional<DisplayCountryDto> findById(Long id);

    Optional<DisplayCountryDto> save(CreateCountryDto host);

    Optional<DisplayCountryDto> update(Long id, CreateCountryDto host);

    void deleteById(Long id);
}
