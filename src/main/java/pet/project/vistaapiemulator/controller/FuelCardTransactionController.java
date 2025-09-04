package pet.project.vistaapiemulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.model.entity.FuelCardTransaction;
import pet.project.vistaapiemulator.service.FuelCardTransactionService;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/transactions")
@RequiredArgsConstructor
public class FuelCardTransactionController {

    private final FuelCardTransactionService service;
    private final PaginationProperties paginationProperties;

    @GetMapping
    public ResponseEntity<PageResponse<FuelCardTransaction>> list(@PathVariable Long customerId,
                                                                  @RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                                  @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit,
                                                                  @RequestParam(required = false) Long dateFrom,
                                                                  @RequestParam(required = false) Long dateTo,
                                                                  @RequestParam(required = false) Long cardId,
                                                                  @RequestParam(required = false) Long locationId) {
        Page<FuelCardTransaction> p = service.list(customerId, page, limit, dateFrom, dateTo, cardId, locationId);
        return ResponseEntity.ok(new PageResponse<>(p.getContent(), p.getTotalElements()));
    }
}
