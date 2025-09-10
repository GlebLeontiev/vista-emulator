package pet.project.vistaapiemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pet.project.vistaapiemulator.model.entity.FuelCardTransaction;

public interface FuelCardTransactionRepository extends JpaRepository<FuelCardTransaction, Long>, JpaSpecificationExecutor<FuelCardTransaction> {

    FuelCardTransaction findTopByOrderByRefTransactionIdDesc();
}
