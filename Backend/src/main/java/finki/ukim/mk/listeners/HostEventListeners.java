package finki.ukim.mk.listeners;

import finki.ukim.mk.events.HostCreatedEvent;
import finki.ukim.mk.repository.HostsPerCountryViewRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HostEventListeners {


    private final HostsPerCountryViewRepository hostsPerCountryViewRepository;

    public HostEventListeners(HostsPerCountryViewRepository hostsPerCountryViewRepository) {
        this.hostsPerCountryViewRepository = hostsPerCountryViewRepository;
    }

    @EventListener
    public void onHostCreated(HostCreatedEvent event){
        hostsPerCountryViewRepository.refreshMaterializedView();
    }
}
