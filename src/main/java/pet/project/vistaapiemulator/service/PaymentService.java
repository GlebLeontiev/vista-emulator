package pet.project.vistaapiemulator.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pet.project.vistaapiemulator.model.entity.Payment;
import pet.project.vistaapiemulator.model.enums.PaymentStatus;
import pet.project.vistaapiemulator.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public Page<Payment> list(Long customerId, Integer page, Integer limit, Long dateFrom, Long dateTo, String status) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.DESC, "datetime"));

        Specification<Payment> spec = (root, q, cb) -> {
            Predicate predicate = cb.equal(root.get("customer").get("id"), customerId);
            if (dateFrom != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("datetime"), dateFrom));
            }
            if (dateTo != null){
                predicate = cb.and(predicate, cb.lessThan(root.get("datetime"), dateTo));
            }
            if (status != null && !status.isBlank()){
                predicate = cb.and(predicate, cb.equal(root.get("status"), Enum.valueOf(PaymentStatus.class, status.toUpperCase())));
            }
            return predicate;
        };

        return repository.findAll(spec, pageable);
    }
}
