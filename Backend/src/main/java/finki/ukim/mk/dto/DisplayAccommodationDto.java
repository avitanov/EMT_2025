package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.enumerations.Category;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayAccommodationDto(
        Long id,
        String name,
        Category category,
        Integer numRooms,
        Boolean isReserved,
        DisplayHostDto host
) {
    public static DisplayAccommodationDto from(Accommodation accommodation) {
        return new DisplayAccommodationDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getNumRooms(),
                accommodation.getIsReserved(),
                DisplayHostDto.from(accommodation.getHost())
        );
    }

    public static List<DisplayAccommodationDto> from(List<Accommodation> accommodationList) {
        return accommodationList.stream().map(DisplayAccommodationDto::from).collect(Collectors.toList());
    }
    public Accommodation toAccomodation(Host host) {
        return new Accommodation(name,category,isReserved,numRooms,host);
    }


}
