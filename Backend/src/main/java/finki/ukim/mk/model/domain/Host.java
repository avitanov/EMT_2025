package finki.ukim.mk.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @ManyToOne
    private Country country;

    @ManyToMany
    @JoinTable(
            name="guest_host_list",
            joinColumns = @JoinColumn(name = "host_list_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_list_id")
    )
    private List<Guest> guestList;


    public Host() {
    this.guestList=new ArrayList<>();
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

    public Host(String name, String surname, Country country) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.guestList=new ArrayList<>();
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
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

    public Host(Long id, String name, String surname, Country country,List<Guest> guestList) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.guestList=guestList;
    }
    public Host( String name, String surname, Country country,List<Guest> guestList) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.guestList=guestList;
    }


}
