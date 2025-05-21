package finki.ukim.mk.events;


import finki.ukim.mk.model.domain.Host;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HostCreatedEvent extends ApplicationEvent {

    private final LocalDateTime when;


    public HostCreatedEvent(Host source){
        super(source);
        when=LocalDateTime.now();
    }
}