package finki.ukim.mk.repository;

import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.projections.HostProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository extends JpaRepository<Host,Long> {

    @Query("SELECT h.name as name, h.surname as surname FROM Host h")
    List<HostProjection> takeNameAndSurnameByProjection();
}
