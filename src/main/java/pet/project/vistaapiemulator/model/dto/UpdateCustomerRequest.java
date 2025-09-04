package pet.project.vistaapiemulator.model.dto;

import lombok.Data;
import pet.project.vistaapiemulator.model.common.Address;
import pet.project.vistaapiemulator.model.common.Contact;
import pet.project.vistaapiemulator.model.enums.CustomerStatus;

import java.math.BigDecimal;

@Data
public class UpdateCustomerRequest {
    private CustomerStatus status;

    private BigDecimal discount;

    private Boolean rebateAsCredit;

    private Long amountOfGallonsForLastTwoMonths;

    private String agreementLink;

    private Address address;

    private Contact contact;
}
