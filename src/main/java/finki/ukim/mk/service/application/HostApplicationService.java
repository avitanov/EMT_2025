package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.CreateHostDto;
import finki.ukim.mk.dto.DisplayHostDto;

import java.util.List;
import java.util.Optional;

public interface HostApplicationService {
    List<DisplayHostDto> findAll();

    Optional<DisplayHostDto> findById(Long id);

    Optional<DisplayHostDto> save(CreateHostDto host);

    Optional<DisplayHostDto> update(Long id, CreateHostDto host);

    void deleteById(Long id);

}
