package finki.ukim.mk.web;

import finki.ukim.mk.dto.AccommodationPerCategoryDTO;
import finki.ukim.mk.dto.CreateAccommodationDto;
import finki.ukim.mk.dto.DisplayAccommodationDto;
import finki.ukim.mk.service.application.AccommodationApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
@Tag(name = "Accommodations API", description = "Endpoints for managing accommodations") // Adjusted for clarity
public class AccommodationController {

    private final AccommodationApplicationService accommodationApplicationService;

    public AccommodationController(AccommodationApplicationService accommodationApplicationService) {
        this.accommodationApplicationService = accommodationApplicationService;
    }

    @Operation(summary = "Get all accommodations", description = "Retrieves a list of all accommodations.")
    @GetMapping
    public List<DisplayAccommodationDto> findAll() {
        return accommodationApplicationService.findAll();
    }

    @Operation(summary = "Get all available accommodations", description = "Retrieves a list of accommodations that are not reserved.")
    @GetMapping("/available")
    public List<DisplayAccommodationDto> findAllAvailable() {
        return accommodationApplicationService.findAvailableAccomodations();
    }


    @Operation(summary = "Get accommodation by ID", description = "Finds an accommodation by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayAccommodationDto> findById(@PathVariable Long id) {
        return accommodationApplicationService.findById(id)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get accommodation statistics", description = "Reserved Accommodation Statistics per every category")
    @GetMapping("/statistics")
    public List<AccommodationPerCategoryDTO> statistics() {
        return accommodationApplicationService.statistics();
    }

    @Operation(summary = "Add a new accommodation", description = "Creates a new accommodation entry.")
    @PostMapping("/add")
    public ResponseEntity<DisplayAccommodationDto> save(@RequestBody CreateAccommodationDto createAccommodationDtoDto) {
        return accommodationApplicationService.save(createAccommodationDtoDto)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Update an accommodation", description = "Updates an existing accommodation by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayAccommodationDto> update(@PathVariable Long id, @RequestBody CreateAccommodationDto createAccommodationDtoDto) {
        return accommodationApplicationService.update(id, createAccommodationDtoDto)
                .map(acc -> ResponseEntity.ok().body(acc))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Rent an accommodation", description = "Marks an accommodation as reserved.")
    @PostMapping("/rent/{id}")
    public ResponseEntity<Void> rent(@PathVariable Long id) {
        if (accommodationApplicationService.findById(id).isPresent()) {
            accommodationApplicationService.rentAccommodation(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an accommodation", description = "Deletes an accommodation by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (accommodationApplicationService.findById(id).isPresent()) {
            accommodationApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
