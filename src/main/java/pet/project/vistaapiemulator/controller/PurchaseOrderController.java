package pet.project.vistaapiemulator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.model.dto.CreateOrderRequest;
import pet.project.vistaapiemulator.model.entity.PurchaseOrder;
import pet.project.vistaapiemulator.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService service;
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<PurchaseOrder> create(@PathVariable Long customerId,
                                                @Valid @RequestBody CreateOrderRequest req,
                                                @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        return ResponseEntity.ok(service.create(customerId, req, idempotencyKey));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PurchaseOrder> get(@PathVariable Long customerId, @PathVariable Long orderId) {
        return ResponseEntity.ok(service.get(customerId, orderId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PurchaseOrder>> list(@PathVariable Long customerId,
                                                            @RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                            @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit) {
        Page<PurchaseOrder> p = service.list(customerId, page, limit);
        return ResponseEntity.ok(new PageResponse<>(p.getContent(), p.getTotalElements()));
    }
}
