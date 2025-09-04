package pet.project.vistaapiemulator.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.vistaapiemulator.exceptions.NotFoundException;
import pet.project.vistaapiemulator.model.dto.AssignCardRequest;
import pet.project.vistaapiemulator.model.entity.FuelCard;
import pet.project.vistaapiemulator.model.enums.FuelCardStatus;
import pet.project.vistaapiemulator.repository.FuelCardRepository;
import pet.project.vistaapiemulator.repository.PurchaseOrderRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class FuelCardService {

    private final FuelCardRepository repository;

    public FuelCard get(Long customerId, Long cardId) {
        FuelCard card = repository.findById(cardId).orElseThrow(() -> new NotFoundException("Fuel card not found"));
        if (card.getCustomer() == null || card.getCustomer().getId() == null || !card.getCustomer().getId().equals(customerId)) {
            throw new NotFoundException("Fuel card not found");
        }
        return card;
    }

    public Page<FuelCard> list(Long customerId, Integer page, Integer limit, String status, Long orderId) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.DESC, "id"));

        Specification<FuelCard> spec = (root, q, cb) -> {
            Predicate predicate = cb.equal(root.get("customer").get("id"), customerId);
            if (status != null && !status.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), Enum.valueOf(FuelCardStatus.class, status.toUpperCase())));
            }
            if (orderId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("order").get("id"), orderId));
            }
            return predicate;
        };

        return repository.findAll(spec, pageable);
    }

    @Transactional
    public String assign(Long customerId, Long cardId, AssignCardRequest req) {
        FuelCard card = get(customerId, cardId);
        card.setFullName(req.getFullName());
        card.setEmail(req.getEmail());

        String pin =Integer.toString(new Random().nextInt(9000) + 1000);
        card.setPin(pin);
        card.setStatus(FuelCardStatus.ACTIVE);
        repository.save(card);

        return pin;
    }

    @Transactional
    public void unassign(Long customerId, Long cardId) {
        FuelCard card = get(customerId, cardId);
        card.setFullName(null);
        card.setEmail(null);

        repository.save(card);
    }

    @Transactional
    public void block(Long customerId, Long cardId) {
        FuelCard card = get(customerId, cardId);
        card.setStatus(FuelCardStatus.INACTIVE);

        repository.save(card);
    }

    @Transactional
    public void unblock(Long customerId, Long cardId) {
        FuelCard card = get(customerId, cardId);
        card.setStatus(FuelCardStatus.ACTIVE);

        repository.save(card);
    }
}
