package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Guest;

import java.util.List;
import java.util.stream.Collectors;

public record CreateGuestDto(
        String name,
        String surname,
        Long country
) {
    public static CreateGuestDto from(Guest host) {
        return new CreateGuestDto(
                host.getName(),
                host.getSurname(),
                host.getCountry().getId()
        );
    }

    public static List<CreateGuestDto> from(List<Guest> guestList) {
        return guestList.stream().map(CreateGuestDto::from).collect(Collectors.toList());
    }

    public Guest toGuest(Country country) {
        return new Guest(name,surname,country);
    }
}
