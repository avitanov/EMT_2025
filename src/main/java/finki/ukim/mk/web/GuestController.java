package finki.ukim.mk.web;

import finki.ukim.mk.dto.CreateGuestDto;
import finki.ukim.mk.dto.DisplayGuestDto;
import finki.ukim.mk.service.application.GuestApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest API", description = "Endpoints for managing guests")
public class GuestController {

    private final GuestApplicationService guestApplicationService;

    public GuestController(GuestApplicationService guestApplicationService) {
        this.guestApplicationService = guestApplicationService;
    }

    @Operation(summary = "Get all guests", description = "Retrieves a list of all available guests.")
    @GetMapping
    public List<DisplayGuestDto> findAll() {
        return guestApplicationService.findAll();
    }

    @Operation(summary = "Get guest by ID", description = "Finds a guest by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayGuestDto> findById(@PathVariable Long id) {
        return guestApplicationService.findById(id)
                .map(guest -> ResponseEntity.ok().body(guest))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Add a new guest", description = "Creates a new guest.")
    @PostMapping("/add")
    public ResponseEntity<DisplayGuestDto> save(@RequestBody CreateGuestDto createGuestDto) {
        return guestApplicationService.save(createGuestDto)
                .map(guest -> ResponseEntity.ok().body(guest))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing guest", description = "Updates a guest by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayGuestDto> update(@PathVariable Long id, @RequestBody CreateGuestDto createGuestDto) {
        return guestApplicationService.update(id, createGuestDto)
                .map(guest -> ResponseEntity.ok().body(guest))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a guest", description = "Deletes a guest by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (guestApplicationService.findById(id).isPresent()) {
            guestApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
