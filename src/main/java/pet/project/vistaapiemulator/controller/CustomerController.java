package pet.project.vistaapiemulator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.model.dto.CreateCustomerRequest;
import pet.project.vistaapiemulator.model.dto.UpdateCustomerRequest;
import pet.project.vistaapiemulator.model.entity.Customer;
import pet.project.vistaapiemulator.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CreateCustomerRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> get(@PathVariable Long customerId) {
        return ResponseEntity.ok(service.get(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Void> update(@PathVariable Long customerId, @Valid @RequestBody UpdateCustomerRequest req) {
        service.update(customerId, req);
        return ResponseEntity.ok().build();
    }
}
