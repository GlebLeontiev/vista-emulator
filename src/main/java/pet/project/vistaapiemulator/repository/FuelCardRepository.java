package pet.project.vistaapiemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pet.project.vistaapiemulator.model.entity.FuelCard;

public interface FuelCardRepository extends JpaRepository<FuelCard, Long>, JpaSpecificationExecutor<FuelCard> {
}
