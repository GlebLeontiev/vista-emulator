package pet.project.vistaapiemulator.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.vistaapiemulator.model.dto.CreateOrderRequest;
import pet.project.vistaapiemulator.model.entity.Customer;
import pet.project.vistaapiemulator.model.entity.PurchaseOrder;
import pet.project.vistaapiemulator.model.enums.OrderStatus;
import pet.project.vistaapiemulator.repository.PurchaseOrderRepository;
import pet.project.vistaapiemulator.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final CustomerService customerService;

    @Transactional
    public PurchaseOrder create(Long customerId, CreateOrderRequest req, String idempotencyKey) {
        Customer customer = customerService.get(customerId);

        PurchaseOrder order = PurchaseOrder.builder()
                .customer(customer)
                .status(OrderStatus.NEW)
                .cardsQuantity(req.getCardsQuantity())
                .shippingAddress(req.getShippingAddress())
                .shippingContact(req.getShippingContact())
                .build();
        return repository.save(order);
    }

    public PurchaseOrder get(Long customerId, Long orderId) {
        PurchaseOrder order = repository.findById(orderId).orElseThrow(() -> new NotFoundException("Purchase order not found"));
        if (order.getCustomer() == null || order.getCustomer().getId() == null || !order.getCustomer().getId().equals(customerId)){
            throw new NotFoundException("Purchase order not found");
        }
        return order;
    }

    public Page<PurchaseOrder> list(Long customerId, int page, int limit) {
        Pageable pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by(Sort.Direction.DESC, "id"));

        Specification<PurchaseOrder> spec = (root, q, cb) -> {
            Predicate byCustomer = cb.equal(root.get("customer").get("id"), customerId);
            return cb.and(byCustomer);
        };

        return repository.findAll(spec, pageable);
    }
}
