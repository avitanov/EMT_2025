package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Accommodation;
import finki.ukim.mk.model.enumerations.Category;

public record AccommodationDetailsDTO(

        Long id,
        String name,
        Category category,
        Integer numRooms,
        Boolean isReserved,
        String hostName,
        String hostSurname,
        String hostCountryName,
        String hostCountryContinent
) {
    public static AccommodationDetailsDTO from(Accommodation accommodation) {
        return new AccommodationDetailsDTO(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getCategory(),
                accommodation.getNumRooms(),
                accommodation.getIsReserved(),
                accommodation.getHost().getName(),
                accommodation.getHost().getSurname(),
                accommodation.getHost().getCountry().getName(),
                accommodation.getHost().getCountry().getContinent()
                );
    }

}
