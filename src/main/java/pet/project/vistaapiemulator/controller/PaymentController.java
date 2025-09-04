package pet.project.vistaapiemulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.model.entity.Payment;
import pet.project.vistaapiemulator.service.PaymentService;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    private final PaginationProperties paginationProperties;

    @GetMapping
    public ResponseEntity<PageResponse<Payment>> list(@PathVariable Long customerId,
                                                      @RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                      @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit,
                                                      @RequestParam(required = false) Long dateFrom,
                                                      @RequestParam(required = false) Long dateTo,
                                                      @RequestParam(required = false) String status) {
        Page<Payment> p = service.list(customerId, page, limit, dateFrom, dateTo, status);
        return ResponseEntity.ok(new PageResponse<>(p.getContent(), p.getTotalElements()));
    }
}
