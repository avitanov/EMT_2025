package finki.ukim.mk.web;

import finki.ukim.mk.dto.CreateAccommodationDto;
import finki.ukim.mk.dto.CreateCountryDto;
import finki.ukim.mk.dto.DisplayAccommodationDto;
import finki.ukim.mk.dto.DisplayCountryDto;
import finki.ukim.mk.model.domain.Country;
import finki.ukim.mk.service.application.CountryApplicationService;
import finki.ukim.mk.service.domain.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country API", description = "Endpoints for managing countries")
public class CountryController {

    private final CountryApplicationService countryApplicationService;

    public CountryController(CountryApplicationService countryApplicationService) {
        this.countryApplicationService = countryApplicationService;
    }

    @Operation(summary = "Get all countries", description = "Retrieves a list of all available countries.")
    @GetMapping
    public ResponseEntity<List<DisplayCountryDto>> findAll() {
        List<DisplayCountryDto> countries = countryApplicationService.findAll();
        return ResponseEntity.ok(countries);
    }

    @Operation(summary = "Get country by ID", description = "Finds an country by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayCountryDto> findById(@PathVariable Long id) {
        return countryApplicationService.findById(id)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new country", description = "Creates a new country entry.")
    @PostMapping("/add")
    public ResponseEntity<DisplayCountryDto> save(@RequestBody CreateCountryDto createCountryDto) {
        return countryApplicationService.save(createCountryDto)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an country", description = "Updates an existing country by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayCountryDto> update(@PathVariable Long id, @RequestBody CreateCountryDto createCountryDto) {
        return countryApplicationService.update(id, createCountryDto)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Delete a country", description = "Deletes a country by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (countryApplicationService.findById(id).isPresent()) {
            countryApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
