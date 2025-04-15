package finki.ukim.mk.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @ManyToOne
    private Country country;

    @ManyToMany(mappedBy = "guestList")
    private List<Host> hostList;

    public Guest() {
    }

    public List<Host> getHostList() {
        return hostList;
    }

    public void setHostList(List<Host> hostList) {
        this.hostList = hostList;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Guest(String name, String surname, Country country) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.hostList=new ArrayList<>();
    }

    public Guest(Long id, String name, String surname, Country country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.country=country;
        this.hostList=new ArrayList<>();
    }
    public Guest(Long id, String name, String surname, Country country, List<Host> hostList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.hostList = hostList;
    }
}
