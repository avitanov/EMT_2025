package finki.ukim.mk.model.domain.views;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Data
@Entity
@Subselect("SELECT * FROM  emt_shop.public.hosts_per_country")
@Immutable
public class HostsPerCountryView {

    @Id
    @Column(name = "country_id")
    @JsonProperty
    private Long countryId;



    @Column(name = "num_hosts")
    @JsonProperty
    private Integer numHosts;

    public Long getCountryId() {
        return countryId;
    }

    public Integer getNumHosts() {
        return numHosts;
    }
}