package finki.ukim.mk.dto;

import finki.ukim.mk.model.domain.Country;

import java.util.List;

public record DisplayCountryDto(
        Long id,
        String name,
        String continent
) {
    public static DisplayCountryDto from(Country country) {
        return new DisplayCountryDto(country.getId(), country.getName(), country.getContinent());
    }

    public Country toCountry() {
        return new Country(id,name, continent);
    }

    public static List<DisplayCountryDto> from(List<Country> countryList) {
        return countryList.stream().map(DisplayCountryDto::from).toList();
    }

}
