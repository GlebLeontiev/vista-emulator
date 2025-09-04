package pet.project.vistaapiemulator.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pet.project.vistaapiemulator.config.PaginationProperties;
import pet.project.vistaapiemulator.model.entity.FuelStation;
import pet.project.vistaapiemulator.model.common.PageResponse;
import pet.project.vistaapiemulator.service.FuelStationService;

@RestController
@RequestMapping("/api/v1/fuel-stations")
@RequiredArgsConstructor
public class FuelStationController {

    private final FuelStationService service;
    private final PaginationProperties paginationProperties;

    @GetMapping
    public ResponseEntity<PageResponse<FuelStation>> list(@RequestParam(defaultValue = "#{paginationProperties.defaultPage}") Integer page,
                                                          @RequestParam(defaultValue = "#{paginationProperties.defaultLimit}") Integer limit) {
        Page<FuelStation> p = service.list(page, limit);
        return ResponseEntity.ok(new PageResponse<>(p.getContent(), p.getTotalElements()));
    }
}
