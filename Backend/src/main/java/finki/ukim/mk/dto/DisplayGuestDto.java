package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.model.domain.Guest;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayGuestDto(
        Long id,
        String name,
        String surname,
        DisplayCountryDto country,
        List<DisplayAccommodationDto> accommodationList
) {
    public static DisplayGuestDto from(Guest guest) {
        return new DisplayGuestDto(
                guest.getId(),
                guest.getName(),
                guest.getSurname(),
                DisplayCountryDto.from(guest.getCountry()),
                DisplayAccommodationDto.from(guest.getWishList())
        );
    }

    public static List<DisplayGuestDto> from(List<Guest> guestList) {
        return guestList.stream().map(DisplayGuestDto::from).collect(Collectors.toList());
    }
    public Guest toGuest(Country country) {
        return new Guest(name,surname,country);
    }

}
