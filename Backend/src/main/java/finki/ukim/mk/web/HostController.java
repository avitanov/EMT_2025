package finki.ukim.mk.web;

import finki.ukim.mk.dto.CreateHostDto;
import finki.ukim.mk.dto.DisplayHostDto;
import finki.ukim.mk.model.domain.views.HostsPerCountryView;
import finki.ukim.mk.model.projections.HostProjection;
import finki.ukim.mk.service.application.HostApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hosts")
@Tag(name = "Host API", description = "Endpoints for managing hosts")
public class HostController {

    private final HostApplicationService hostApplicationService;

    public HostController(HostApplicationService hostApplicationService) {
        this.hostApplicationService = hostApplicationService;
    }

    @Operation(summary = "Get all hosts", description = "Retrieves a list of all available hosts.")
    @GetMapping
    public List<DisplayHostDto> findAll() {
        return hostApplicationService.findAll();
    }

    @Operation(summary = "Get host by ID", description = "Finds a host by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayHostDto> findById(@PathVariable Long id) {
        return hostApplicationService.findById(id)
                .map(host -> ResponseEntity.ok().body(host))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get num hosts per country", description = "Get num hosts per country")
    @GetMapping("/by-country")
    public List<HostsPerCountryView> findNumHostsPerCountry() {
        return hostApplicationService.findNumHostsPerCountry();
    }
    @Operation(summary = "Get projection", description = "Get projection for host name and surname")
    @GetMapping("/names")
    public List<HostProjection> findAllProjections() {
        return hostApplicationService.findAllProjections();
    }

    @Operation(summary = "Add a new guest", description = "Add guest.")
    @PostMapping("/{id}/addGuest")
    public ResponseEntity<DisplayHostDto> save(@PathVariable Long id,@RequestParam Long guestId) {
        if (hostApplicationService.findById(id).isPresent()) {
            hostApplicationService.reservation(id,guestId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Add a new host", description = "Creates a new host.")
    @PostMapping("/add")
    public ResponseEntity<DisplayHostDto> save(@RequestBody CreateHostDto createHostDto) {
        return hostApplicationService.save(createHostDto)
                .map(host -> ResponseEntity.ok().body(host))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an existing host", description = "Updates a host by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayHostDto> update(@PathVariable Long id, @RequestBody CreateHostDto createHostDto) {
        return hostApplicationService.update(id, createHostDto)
                .map(host -> ResponseEntity.ok().body(host))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a host", description = "Deletes a host by its ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (hostApplicationService.findById(id).isPresent()) {
            hostApplicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

