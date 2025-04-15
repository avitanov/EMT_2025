package finki.ukim.mk.service.domain.impl;

import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.domain.Guest;
import finki.ukim.mk.repository.HostRepository;
import finki.ukim.mk.service.domain.CountryService;
import finki.ukim.mk.service.domain.GuestService;
import finki.ukim.mk.service.domain.HostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HostServiceImpl implements HostService {
    private final HostRepository hostRepository;
    private final CountryService countryService;
    private final GuestService guestService;

    public HostServiceImpl(HostRepository hostRepository,CountryService countryService,GuestService guestService) {
        this.hostRepository = hostRepository;
        this.countryService=countryService;
        this.guestService=guestService;
    }

    @Override
    public List<Host> findAll() {
        return this.hostRepository.findAll();
    }

    @Override
    public Optional<Host> findById(Long id) {
        return this.hostRepository.findById(id);
    }

    @Override
    public Optional<Host> update(Long id, Host host) {
        return hostRepository.findById(id).map(existingHost->{
            if(host.getName()!=null){
                existingHost.setName(host.getName());
            }
            if(host.getSurname()!=null){
                existingHost.setSurname(host.getSurname());
            }
            if(host.getCountry()!=null && countryService.findById(host.getCountry().getId()).isPresent()){
                existingHost.setCountry(host.getCountry());
            }
            return hostRepository.save(existingHost);
        });
    }

    @Override
    public void reservation(Long id, Long guestId) {
        Host hostTmp=this.findById(id).get();
        Guest guestTmp=this.guestService.findById(guestId).get();
        hostTmp.getGuestList().add(guestTmp);
        guestTmp.getHostList().add(hostTmp);
        this.hostRepository.save(hostTmp);
        this.guestService.save(guestTmp);
    }

    @Override
    public Optional<Host> save(Host host) {
        if(host.getCountry()!=null && countryService.findById(host.getCountry().getId()).isPresent()){
           return Optional.of(this.hostRepository.save(new Host(host.getName(),host.getSurname(),countryService.findById(host.getCountry().getId()).get())));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        this.hostRepository.deleteById(id);
    }
}
