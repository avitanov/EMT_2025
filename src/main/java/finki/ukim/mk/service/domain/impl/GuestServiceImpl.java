package finki.ukim.mk.service.domain.impl;

import finki.ukim.mk.model.domain.Guest;
import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.repository.GuestRepository;
import finki.ukim.mk.service.domain.AccommodationService;
import finki.ukim.mk.service.domain.CountryService;
import finki.ukim.mk.service.domain.GuestService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final CountryService countryService;

    private final AccommodationService accommodationService;

    public GuestServiceImpl(GuestRepository guestRepository, CountryService countryService,@Lazy AccommodationService accommodationService) {
        this.guestRepository = guestRepository;
        this.countryService = countryService;
        this.accommodationService = accommodationService;
    }

    @Override
    public String addToWishList(Long id, Long AccId) {
        Guest guest=guestRepository.findById(id).get();
        Accommodation acc=accommodationService.findById(AccId).get();
        if(!acc.getIsReserved()){
            guest.getWishList().add(acc);
            this.guestRepository.save(guest);
            return "Successfully Added";
        }
        else return "ERROR IT IS ALREADY RESERVED";
    }

    @Override
    public String reserveWishList(Long id) {
        Guest guest=guestRepository.findById(id).get();
        for(Accommodation acc:guest.getWishList()) {
            if(acc.getIsReserved()){
                return "ERROR IT IS ALREADY RESERVED";
            }
        }
        for(Accommodation acc:guest.getWishList()) {
            acc.setIsReserved(true);
            accommodationService.update(acc.getId(),acc);
        }
        return "Successfully Added";
    }

    @Override
    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    @Override
    public Optional<Guest> findById(Long id) {
        return this.guestRepository.findById(id);
    }

    @Override
    public Optional<Guest> update(Long id, Guest guest) {
        return guestRepository.findById(id).map(existingGuest->{
            if(guest.getName()!=null){
                existingGuest.setName(guest.getName());
            }
            if(guest.getSurname()!=null){
                existingGuest.setSurname(guest.getSurname());
            }
            if(guest.getCountry()!=null && countryService.findById(guest.getCountry().getId()).isPresent()){
                existingGuest.setCountry(guest.getCountry());
            }
            return guestRepository.save(existingGuest);
        });
    }

    @Override
    public Optional<Guest> save(Guest guest) {
        if(guest.getCountry()!=null && countryService.findById(guest.getCountry().getId()).isPresent()){
            return Optional.of(this.guestRepository.save(new Guest(guest.getName(),guest.getSurname(),countryService.findById(guest.getCountry().getId()).get())));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        this.guestRepository.deleteById(id);
    }
}
