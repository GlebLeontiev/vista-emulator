package pet.project.vistaapiemulator.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pet.project.vistaapiemulator.model.common.Address;
import pet.project.vistaapiemulator.model.common.Contact;

import java.math.BigDecimal;

@Data
public class CreateCustomerRequest {
    @NotBlank
    private String name;

    private BigDecimal discount;

    private Boolean rebateAsCredit;

    private Long amountOfGallonsForLastTwoMonths;

    private String agreementLink;

    @NotNull
    private Address address;

    @NotNull
    private Contact contact;
}
