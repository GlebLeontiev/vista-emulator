package pet.project.vistaapiemulator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.model.dto.AssignCardRequest;
import pet.project.vistaapiemulator.model.entity.FuelCard;
import pet.project.vistaapiemulator.service.FuelCardService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/cards")
@RequiredArgsConstructor
public class FuelCardController {

    private final FuelCardService service;
    private final PaginationProperties paginationProperties;

    @PostMapping("/{cardId}/assign")
    public ResponseEntity<Map<String, String>> assign(@PathVariable Long customerId,
                                                      @PathVariable Long cardId,
                                                      @Valid @RequestBody AssignCardRequest req) {
        String pin = service.assign(customerId, cardId, req);
        return ResponseEntity.ok(Map.of("pin", pin));
    }

    @PostMapping("/{cardId}/unassign")
    public ResponseEntity<Void> unassign(@PathVariable Long customerId,
                                         @PathVariable Long cardId) {
        service.unassign(customerId, cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/block")
    public ResponseEntity<Void> block(@PathVariable Long customerId, @PathVariable Long cardId) {
        service.block(customerId, cardId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{cardId}/unblock")
    public ResponseEntity<Void> unblock(@PathVariable Long customerId, @PathVariable Long cardId) {
        service.unblock(customerId, cardId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<FuelCard>> list(@PathVariable Long customerId,
                                                       @RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                       @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit,
                                                       @RequestParam(required = false) String status,
                                                       @RequestParam(required = false) Long orderId) {
        Page<FuelCard> p = service.list(customerId, page, limit, status, orderId);
        return ResponseEntity.ok(new PageResponse<>(p.getContent(), p.getTotalElements()));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<FuelCard> get(@PathVariable Long customerId, @PathVariable Long cardId) {
        return ResponseEntity.ok(service.get(customerId, cardId));
    }
}
