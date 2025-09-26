package pet.project.vistaapiemulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.model.entity.Alert;
import pet.project.vistaapiemulator.service.AlertService;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;
    private final PaginationProperties paginationProperties;

    @PostMapping
    public ResponseEntity<Void> createAlerts(@PathVariable Long customerId) {
        alertService.createAlertsForCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<Alert>> listAlerts(@PathVariable Long customerId,
                                                          @RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                          @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit,
                                                          @RequestParam(required = false) Long dateFrom,
                                                          @RequestParam(required = false) Long dateTo) {
        Page<Alert> alerts = alertService.list(customerId, page, limit, dateFrom, dateTo);
        return ResponseEntity.ok(new PageResponse<>(alerts.getContent(), alerts.getTotalElements()));
    }
}
