package finki.ukim.mk.service.application.impl;

import finki.ukim.mk.dto.CreateHostDto;
import finki.ukim.mk.dto.DisplayAccommodationDto;
import finki.ukim.mk.dto.DisplayGuestDto;
import finki.ukim.mk.dto.DisplayHostDto;
import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.domain.views.HostsPerCountryView;
import finki.ukim.mk.model.projections.HostProjection;
import finki.ukim.mk.service.application.HostApplicationService;
import finki.ukim.mk.service.domain.CountryService;
import finki.ukim.mk.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostApplicationServiceImpl implements HostApplicationService {
    private final HostService hostService;
    private final CountryService countryService;

    public HostApplicationServiceImpl(HostService hostService, CountryService countryService) {
        this.hostService = hostService;
        this.countryService = countryService;
    }


    @Override
    public List<DisplayHostDto> findAll() {
        return hostService.findAll().stream().map(DisplayHostDto::from).toList();
    }

    @Override
    public void reservation(Long id, Long guestId) {
        this.hostService.reservation(id, guestId);
    }

    @Override
    public List<HostsPerCountryView> findNumHostsPerCountry() {
        return hostService.findNumHostsPerCountry();
    }

    @Override
    public List<HostProjection> findAllProjections() {
        return hostService.findAllProjections();
    }

    @Override
    public Optional<DisplayHostDto> findById(Long id) {
        return hostService.findById(id).map(DisplayHostDto::from);
    }

    @Override
    public Optional<DisplayHostDto> save(CreateHostDto hostDto) {
        Optional<Country> country = countryService.findById(hostDto.country());

        if (country.isPresent()) {
            return hostService.save(hostDto.toHost(country.get()))
                    .map(DisplayHostDto::from);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DisplayHostDto> update(Long id, CreateHostDto host) {
        Optional<Country> country = countryService.findById(host.country());


        return hostService.update(id,
                        host.toHost(
                                country.orElse(null)
                        )
                )
                .map(DisplayHostDto::from);
    }

    @Override
    public void deleteById(Long id) {
        hostService.deleteById(id);
    }
}
