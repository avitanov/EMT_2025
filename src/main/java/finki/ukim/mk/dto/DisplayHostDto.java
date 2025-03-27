package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Host;

public record DisplayHostDto(
        Long id,
        String name,
        String surname,
        DisplayCountryDto country
) {
    public static DisplayHostDto from(Host host) {
        return new DisplayHostDto(
                host.getId(),
                host.getName(),
                host.getSurname(),
                DisplayCountryDto.from(host.getCountry())
        );
    }
    public Host toHost(Country country) {
        return new Host(name,surname,country);
    }


}
