package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.domain.Host;
import finki.ukim.mk.model.enumerations.Category;

import java.util.List;
import java.util.stream.Collectors;

public record CreateAccommodationDto(
        String name,
        Category category,
        Integer numRooms,
        Long host
) {
    public static CreateAccommodationDto from(Accommodation accommodation) {
        return new CreateAccommodationDto(
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getNumRooms(),
                accommodation.getHost().getId()
        );
    }

    public static List<CreateAccommodationDto> from(List<Accommodation> accommodationList) {
        return accommodationList.stream().map(CreateAccommodationDto::from).collect(Collectors.toList());
    }

    public Accommodation toAccommodation(Host host) {
        return new Accommodation(name,category,Boolean.FALSE,numRooms,host);
    }

}
