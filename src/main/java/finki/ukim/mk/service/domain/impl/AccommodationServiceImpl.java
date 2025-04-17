package finki.ukim.mk.service.domain.impl;

import finki.ukim.mk.dto.AccommodationPerCategoryDTO;
import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.enumerations.Category;
import finki.ukim.mk.repository.AccommodationRepository;
import finki.ukim.mk.repository.CountryRepository;
import finki.ukim.mk.repository.HostRepository;
import finki.ukim.mk.service.domain.AccommodationService;

import finki.ukim.mk.service.domain.CountryService;
import finki.ukim.mk.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final HostService hostService;

    public AccommodationServiceImpl(AccommodationRepository accommodationRepository, HostService hostService) {
        this.accommodationRepository = accommodationRepository;
        this.hostService = hostService;
    }

    @Override
    public List<Accommodation> findAll() {

        return this.accommodationRepository.findAll();
    }

    @Override
    public List<Accommodation> findAvailableAccomodations() {
        return accommodationRepository.findAllByIsReservedFalse();
    }

    @Override
    public Optional<Accommodation> findById(Long id) {

        return accommodationRepository.findById(id);
    }

    @Override
    public Optional<Accommodation> update(Long id, Accommodation accommodation) {

        return accommodationRepository.findById(id).map(existingAcc->{
            if (accommodation.getName() != null) {
                existingAcc.setName(accommodation.getName());
            }
            if(accommodation.getIsReserved()!=null){
                existingAcc.setIsReserved(accommodation.getIsReserved());
            }
            if(accommodation.getCategory()!=null){
                    existingAcc.setCategory(accommodation.getCategory());
            }
            if(accommodation.getNumRooms()!=null && accommodation.getNumRooms()>=0){
                existingAcc.setNumRooms(accommodation.getNumRooms());
            }
            if(accommodation.getHost()!=null && hostService.findById(accommodation.getHost().getId()).isPresent()){
                existingAcc.setHost(hostService.findById(accommodation.getHost().getId()).get());
            }

            return this.accommodationRepository.save(existingAcc);
        });
    }

    @Override
    public Optional<Accommodation> save(Accommodation accommodation) {
        if(accommodation.getCategory()!=null && accommodation.getHost()!=null && hostService.findById(accommodation.getHost().getId()).isPresent()){
            return Optional.of(this.accommodationRepository.save(new Accommodation(accommodation.getName(),accommodation.getCategory(),accommodation.getIsReserved(),accommodation.getNumRooms(),hostService.findById(accommodation.getHost().getId()).get())));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        this.accommodationRepository.deleteById(id);
    }

    @Override
    public void rentAccommodation(Long id) {
        Accommodation tmp=this.accommodationRepository.findById(id).get();
        tmp.setIsReserved(Boolean.TRUE);
        this.accommodationRepository.save(tmp);
    }

    @Override
    public List<AccommodationPerCategoryDTO> statistics() {
        return this.accommodationRepository.countReservedByCategory().stream().map((obj)->new AccommodationPerCategoryDTO(String.valueOf(obj[0]),((Number) obj[1]).intValue())).collect(Collectors.toList());
    }
}
