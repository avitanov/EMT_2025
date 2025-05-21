package finki.ukim.mk.service.application.impl;

import finki.ukim.mk.dto.AccommodationDetailsDTO;
import finki.ukim.mk.dto.AccommodationPerCategoryDTO;
import finki.ukim.mk.dto.CreateAccommodationDto;
import finki.ukim.mk.dto.DisplayAccommodationDto;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.domain.views.AccommodationsPerHostView;
import finki.ukim.mk.service.application.AccommodationApplicationService;
import finki.ukim.mk.service.domain.AccommodationService;
import finki.ukim.mk.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccommodationApplicationServiceImpl implements AccommodationApplicationService {
    private final AccommodationService accommodationService;
    private final HostService hostService;

    public AccommodationApplicationServiceImpl(AccommodationService accommodationService, HostService hostService) {
        this.accommodationService = accommodationService;
        this.hostService = hostService;
    }

    @Override
    public List<AccommodationsPerHostView> findAllAccommodationsByHost() {
        return accommodationService.findAllAccommodationsByHost();
    }

    @Override
    public Optional<DisplayAccommodationDto> update(Long id, CreateAccommodationDto accommodationDto) {
        Optional<Host> host = hostService.findById(accommodationDto.host());


        return accommodationService.update(id,
                accommodationDto.toAccommodation(
                                host.orElse(null)
                        )
                )
                .map(DisplayAccommodationDto::from);

    }

    @Override
    public Optional<DisplayAccommodationDto> save(CreateAccommodationDto accommodationDto) {
        Optional<Host> host = hostService.findById(accommodationDto.host());

        if (host.isPresent()) {
            return accommodationService.save(accommodationDto.toAccommodation(host.get()))
                    .map(DisplayAccommodationDto::from);
        }
        return Optional.empty();

    }

    @Override
    public Optional<DisplayAccommodationDto> findById(Long id) {
        return accommodationService.findById(id).map(DisplayAccommodationDto::from);
    }

    @Override
    public Optional<AccommodationDetailsDTO> findByIdDetails(Long id) {
        return accommodationService.findByIdDetails(id).map(AccommodationDetailsDTO::from);
    }

    @Override
    public List<DisplayAccommodationDto> findAll() {
        return accommodationService.findAll().stream().map(DisplayAccommodationDto::from).toList();

    }

    @Override
    public void deleteById(Long id) {
        accommodationService.deleteById(id);
    }

    @Override
    public List<DisplayAccommodationDto> findAvailableAccomodations() {
        return accommodationService.findAvailableAccomodations().stream().map(DisplayAccommodationDto::from).toList();

    }

    @Override
    public void rentAccommodation(Long id) {
        accommodationService.rentAccommodation(id);
    }

    @Override
    public List<AccommodationPerCategoryDTO> statistics() {
        return this.accommodationService.statistics();
    }
}
