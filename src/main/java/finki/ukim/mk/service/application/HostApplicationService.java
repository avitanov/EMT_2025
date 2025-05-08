package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.CreateHostDto;
import finki.ukim.mk.dto.DisplayGuestDto;
import finki.ukim.mk.dto.DisplayHostDto;
import finki.ukim.mk.model.domain.views.HostsPerCountryView;
import finki.ukim.mk.model.projections.HostProjection;

import java.util.List;
import java.util.Optional;

public interface HostApplicationService {
    List<HostsPerCountryView> findNumHostsPerCountry();

    List<HostProjection> findAllProjections();

    List<DisplayHostDto> findAll();

    Optional<DisplayHostDto> findById(Long id);

    Optional<DisplayHostDto> save(CreateHostDto host);

    Optional<DisplayHostDto> update(Long id, CreateHostDto host);

    void deleteById(Long id);
    void reservation(Long id, Long guestId);

}
