package finki.ukim.mk.repository;

import finki.ukim.mk.model.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {
    List<Accommodation> findAllByIsReservedFalse();
}
