package pet.project.vistaapiemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pet.project.vistaapiemulator.model.entity.FuelCard;
import pet.project.vistaapiemulator.model.enums.FuelCardStatus;

import java.util.List;

public interface FuelCardRepository extends JpaRepository<FuelCard, Long>, JpaSpecificationExecutor<FuelCard> {

    List<FuelCard> findFuelCardByStatus(FuelCardStatus status);
}
