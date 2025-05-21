package finki.ukim.mk.service.domain;

import finki.ukim.mk.dto.AccommodationDetailsDTO;
import finki.ukim.mk.dto.AccommodationPerCategoryDTO;
import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.domain.views.AccommodationsPerHostView;
import finki.ukim.mk.repository.AccommodationRepository;

import java.util.List;
import java.util.Optional;

public interface AccommodationService {

    List<Accommodation> findAll();
    List<Accommodation> findAvailableAccomodations();
    Optional<Accommodation> findById(Long id);
    Optional<Accommodation> findByIdDetails(Long id);
    Optional<Accommodation> update(Long id, Accommodation accommodation);

    Optional<Accommodation> save(Accommodation accommodation);

    void deleteById(Long id);

    void rentAccommodation(Long id);

    List<AccommodationPerCategoryDTO> statistics();
    List<AccommodationsPerHostView> findAllAccommodationsByHost();




}
