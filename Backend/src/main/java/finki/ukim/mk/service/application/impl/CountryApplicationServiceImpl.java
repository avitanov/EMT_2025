package finki.ukim.mk.service.application.impl;

import finki.ukim.mk.dto.CreateCountryDto;
import finki.ukim.mk.dto.DisplayCountryDto;
import finki.ukim.mk.service.application.CountryApplicationService;
import finki.ukim.mk.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {

    private CountryService countryService;

    public CountryApplicationServiceImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public List<DisplayCountryDto> findAll() {
        return DisplayCountryDto.from(countryService.findAll());
    }

    @Override
    public Optional<DisplayCountryDto> findById(Long id) {
        return countryService.findById(id).map(DisplayCountryDto::from);
    }

    @Override
    public Optional<DisplayCountryDto> save(CreateCountryDto host) {
        return countryService.save(host.toCountry()).map(DisplayCountryDto::from);
    }

    @Override
    public Optional<DisplayCountryDto> update(Long id, CreateCountryDto host) {
        return countryService.update(id,host.toCountry()).map(DisplayCountryDto::from);
    }

    @Override
    public void deleteById(Long id) {
        countryService.deleteById(id);
    }
}
