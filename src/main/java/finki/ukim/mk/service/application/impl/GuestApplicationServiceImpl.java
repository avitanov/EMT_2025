package finki.ukim.mk.service.application.impl;

import finki.ukim.mk.dto.CreateGuestDto;
import finki.ukim.mk.dto.DisplayGuestDto;
import finki.ukim.mk.dto.DisplayHostDto;
import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.service.application.GuestApplicationService;
import finki.ukim.mk.service.domain.CountryService;
import finki.ukim.mk.service.domain.GuestService;
import finki.ukim.mk.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestApplicationServiceImpl implements GuestApplicationService {

    private final GuestService guestService;
    private final CountryService countryService;

    public GuestApplicationServiceImpl(GuestService guestService, CountryService countryService) {
        this.guestService = guestService;
        this.countryService = countryService;
    }

    @Override
    public List<DisplayGuestDto> findAll() {
        return  guestService.findAll().stream().map(DisplayGuestDto::from).toList();
    }

    @Override
    public Optional<DisplayGuestDto> findById(Long id) {
        return guestService.findById(id).map(DisplayGuestDto::from);
    }

    @Override
    public Optional<DisplayGuestDto> save(CreateGuestDto host) {
        Optional<Country> country = countryService.findById(host.country());

        if (country.isPresent()) {
            return guestService.save(host.toGuest(country.get()))
                    .map(DisplayGuestDto::from);
        }
        return Optional.empty();
    }

    @Override
    public Optional<DisplayGuestDto> update(Long id, CreateGuestDto host) {
        Optional<Country> country = countryService.findById(host.country());


        return guestService.update(id,
                        host.toGuest(
                                country.orElse(null)
                        )
                )
                .map(DisplayGuestDto::from);
    }

    @Override
    public void deleteById(Long id) {
        this.guestService.deleteById(id);
    }
}
