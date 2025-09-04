package pet.project.vistaapiemulator.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pet.project.vistaapiemulator.model.entity.FuelCardTransaction;
import pet.project.vistaapiemulator.repository.FuelCardTransactionRepository;

@Service
@RequiredArgsConstructor
public class FuelCardTransactionService {

    private final FuelCardTransactionRepository repository;

    public Page<FuelCardTransaction> list(Long customerId, Integer page, Integer limit, Long dateFrom, Long dateTo, Long cardId, Long locationId) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.DESC, "datetime"));

        Specification<FuelCardTransaction> spec = (root, q, cb) -> {
            Predicate predicate = cb.equal(root.get("customer").get("id"), customerId);
            if (dateFrom != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("datetime"), dateFrom));
            }
            if (dateTo != null) {
                predicate = cb.and(predicate, cb.lessThan(root.get("datetime"), dateTo));
            }
            if (cardId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("card").get("id"), cardId));
            }
            if (locationId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("station").get("id"), locationId));
            }
            return predicate;
        };

        return repository.findAll(spec, pageable);
    }
}
