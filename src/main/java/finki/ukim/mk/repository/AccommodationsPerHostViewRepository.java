package finki.ukim.mk.repository;

import finki.ukim.mk.model.domain.views.AccommodationsPerHostView;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationsPerHostViewRepository extends JpaRepository<AccommodationsPerHostView,Long> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW emt_shop.public.accommodations_per_host",nativeQuery = true)
    void refreshMaterializedView();
}
