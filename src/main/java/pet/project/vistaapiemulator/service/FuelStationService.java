package pet.project.vistaapiemulator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pet.project.vistaapiemulator.model.entity.FuelStation;
import pet.project.vistaapiemulator.repository.FuelStationRepository;

@Service
@RequiredArgsConstructor
public class FuelStationService {

    private final FuelStationRepository repository;

    public Page<FuelStation> list(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.ASC, "id"));
        return repository.findAll(pageable);
    }
}
