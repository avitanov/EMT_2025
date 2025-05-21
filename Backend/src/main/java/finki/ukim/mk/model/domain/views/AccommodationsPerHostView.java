package finki.ukim.mk.model.domain.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;


@Data
@Entity
@Subselect("SELECT * FROM emt_shop.public.accommodations_per_host")
@Immutable
public class AccommodationsPerHostView {
    @Id
    @Column(name = "host_id")
    @JsonProperty
    private Long hostId;

    @Column(name = "num_accommodations")
    @JsonProperty
    private Integer numAccommodations;
}
