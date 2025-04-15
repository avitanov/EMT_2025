package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Guest;
import finki.ukim.mk.model.domain.Host;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayHostDto(
        Long id,
        String name,
        String surname,
        DisplayCountryDto country,
        List<DisplayGuestDto> guestDtoList
) {
    public static DisplayHostDto from(Host host) {
        return new DisplayHostDto(
                host.getId(),
                host.getName(),
                host.getSurname(),
                DisplayCountryDto.from(host.getCountry()),
                DisplayGuestDto.from(host.getGuestList())
        );
    }
    public static List<DisplayHostDto> from(List<Host> hostList) {
        return hostList.stream().map(DisplayHostDto::from).collect(Collectors.toList());
    }
    public Host toHost(Country country, List<Guest> guestList) {
        return new Host(name,surname,country,guestList);
    }


}
