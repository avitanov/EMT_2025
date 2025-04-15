package finki.ukim.mk.service.domain;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Guest;

import java.util.List;
import java.util.Optional;

public interface GuestService {

    List<Guest> findAll();
    Optional<Guest> findById(Long id);
    Optional<Guest> update(Long id, Guest guest);

    Optional<Guest> save(Guest guest);
    void deleteById(Long id);
}
