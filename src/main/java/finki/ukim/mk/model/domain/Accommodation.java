package finki.ukim.mk.model.domain;

import finki.ukim.mk.model.enumerations.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity

@Data
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private Boolean isReserved;

    private Integer numRooms;

    @ManyToOne
    private Host host;

    public Accommodation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(Boolean reserved) {
        isReserved = reserved;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Accommodation(Long id, String name, Category category, Boolean isReserved, Integer numRooms, Host host) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.isReserved = isReserved;
        this.numRooms = numRooms;
        this.host = host;
    }
    public Accommodation( String name, Category category, Boolean isReserved, Integer numRooms, Host host) {
        this.name = name;
        this.category = category;
        this.isReserved = isReserved;
        this.numRooms = numRooms;
        this.host = host;
    }
}
