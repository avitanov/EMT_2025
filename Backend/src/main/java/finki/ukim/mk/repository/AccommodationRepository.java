package finki.ukim.mk.repository;

import finki.ukim.mk.model.domain.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {
    List<Accommodation> findAllByIsReservedFalse();

    @Query("SELECT a.category, COUNT(a) " +
            "FROM Accommodation a " +
            "WHERE a.isReserved = true " +
            "GROUP BY a.category")
    List<Object[]> countReservedByCategory();

}
