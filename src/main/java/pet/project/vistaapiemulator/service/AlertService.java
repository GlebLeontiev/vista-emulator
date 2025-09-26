package pet.project.vistaapiemulator.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.vistaapiemulator.model.entity.Alert;
import pet.project.vistaapiemulator.model.entity.Customer;
import pet.project.vistaapiemulator.repository.AlertRepository;
import pet.project.vistaapiemulator.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {

    private final AlertRepository alertRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void createAlertsForCustomer(Long id) {
        List<Alert> alertList = new ArrayList<>();
        Customer referenceById = customerRepository.getReferenceById(id);
        long now = System.currentTimeMillis();

        Alert.ALERT_TYPES.forEach(alertType -> {
            Alert alert = Alert.builder()
                    .date(now)
                    .type(alertType)
                    .message(alertType)
                    .customer(referenceById)
                    .build();

            alertList.add(alert);
        });
        alertRepository.saveAll(alertList);
        log.info("Create alerts for customer: {}", id);
    }

    public Page<Alert> list(Long customerId, Integer page, Integer limit, Long dateFrom, Long dateTo) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.DESC, "date"));

        Specification<Alert> spec = (root, q, cb) -> {
            Predicate predicate = cb.equal(root.get("customer").get("id"), customerId);
            if (dateFrom != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("date"), dateFrom));
            }
            if (dateTo != null){
                predicate = cb.and(predicate, cb.lessThan(root.get("date"), dateTo));
            }
            return predicate;
        };

        return alertRepository.findAll(spec, pageable);
    }
}
