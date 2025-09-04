package pet.project.vistaapiemulator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.vistaapiemulator.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
