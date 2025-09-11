package pet.project.vistaapiemulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pet.project.vistaapiemulator.model.entity.FuelCardTransaction;

public interface FuelCardTransactionRepository extends JpaRepository<FuelCardTransaction, Long>, JpaSpecificationExecutor<FuelCardTransaction> {

    @Query("SELECT MAX(f.refTransactionId) FROM FuelCardTransaction f WHERE f.refTransactionId IS NOT NULL")
    String findMaxRefTransactionId();
}
