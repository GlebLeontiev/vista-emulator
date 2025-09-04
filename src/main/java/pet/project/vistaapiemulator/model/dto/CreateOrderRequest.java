package pet.project.vistaapiemulator.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pet.project.vistaapiemulator.model.common.Address;
import pet.project.vistaapiemulator.model.common.Contact;

@Data
public class CreateOrderRequest {
    @NotNull
    @Min(1)
    private Integer cardsQuantity;

    @NotNull
    private Address shippingAddress;

    @NotNull
    private Contact shippingContact;
}
