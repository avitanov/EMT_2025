package finki.ukim.mk.service.application;

import finki.ukim.mk.dto.AccommodationDetailsDTO;
import finki.ukim.mk.dto.AccommodationPerCategoryDTO;
import finki.ukim.mk.dto.CreateAccommodationDto;
import finki.ukim.mk.dto.DisplayAccommodationDto;
import finki.ukim.mk.model.domain.views.AccommodationsPerHostView;

import java.util.List;
import java.util.Optional;

public interface AccommodationApplicationService {

    Optional<DisplayAccommodationDto> update(Long id, CreateAccommodationDto accommodationDto) ;

    Optional<DisplayAccommodationDto> save(CreateAccommodationDto accommodationDto);

    Optional<DisplayAccommodationDto> findById(Long id);
    Optional<AccommodationDetailsDTO> findByIdDetails(Long id);

    List<DisplayAccommodationDto> findAll();

    List<DisplayAccommodationDto> findAvailableAccomodations();

    void deleteById(Long id);
    void rentAccommodation(Long id);

    List<AccommodationPerCategoryDTO> statistics();
    List<AccommodationsPerHostView> findAllAccommodationsByHost();
}
