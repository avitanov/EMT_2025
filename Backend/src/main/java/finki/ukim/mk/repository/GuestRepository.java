package finki.ukim.mk.repository;

import finki.ukim.mk.model.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest,Long> {

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {"wishList"}
    )
    @Query("SELECT u from Guest u")
    List<Guest> loadALl();
}
