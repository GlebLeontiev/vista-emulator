package pet.project.vistaapiemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pet.project.vistaapiemulator.model.entity.FuelStation;

public interface FuelStationRepository extends JpaRepository<FuelStation, Long>, JpaSpecificationExecutor<FuelStation> {
}
