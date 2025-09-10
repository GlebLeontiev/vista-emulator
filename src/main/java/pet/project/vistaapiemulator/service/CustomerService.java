package pet.project.vistaapiemulator.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.project.vistaapiemulator.exceptions.NotFoundException;
import pet.project.vistaapiemulator.model.dto.CreateCustomerRequest;
import pet.project.vistaapiemulator.model.dto.UpdateCustomerRequest;
import pet.project.vistaapiemulator.model.entity.Customer;
import pet.project.vistaapiemulator.model.enums.CustomerStatus;
import pet.project.vistaapiemulator.repository.CustomerRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;

    @Transactional
    public Customer create(CreateCustomerRequest req) {
        Customer customer = Customer.builder()
                .name(req.getName())
                .address(req.getAddress())
                .contact(req.getContact())
                .discount(req.getDiscount())
                .rebateAsCredit(req.getRebateAsCredit())
                .amountOfGallonsForLastTwoMonths(req.getAmountOfGallonsForLastTwoMonths())
                .agreementLink(req.getAgreementLink())
                .balance(BigDecimal.ZERO)
                .status(CustomerStatus.ACTIVE)
                .currentMonthGallons(0L)
                .forecastedFuelAmount(0L)
                .forecastedRebate(BigDecimal.ZERO)
                .paymentLink("https://payments.example.com/pay?token=" + UUID.randomUUID())
                .build();
        Customer save = repository.save(customer);
        log.info("Create customer: name - {} ; id - {}", save.getName(), save.getId());
        return save;
    }

    public Customer get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @Transactional
    public void update(Long id, UpdateCustomerRequest req) {
        Customer customer = get(id);
        if (req.getStatus() != null) customer.setStatus(req.getStatus());
        if (req.getDiscount() != null) customer.setDiscount(req.getDiscount());
        if (req.getRebateAsCredit() != null) customer.setRebateAsCredit(req.getRebateAsCredit());
        if (req.getAmountOfGallonsForLastTwoMonths() != null)
            customer.setAmountOfGallonsForLastTwoMonths(req.getAmountOfGallonsForLastTwoMonths());
        if (req.getAgreementLink() != null) customer.setAgreementLink(req.getAgreementLink());
        if (req.getAddress() != null) customer.setAddress(req.getAddress());
        if (req.getContact() != null) customer.setContact(req.getContact());
        Customer save = repository.save(customer);
        log.info("Update customer: name - {} ; id - {}", save.getName(), save.getId());
    }
}
