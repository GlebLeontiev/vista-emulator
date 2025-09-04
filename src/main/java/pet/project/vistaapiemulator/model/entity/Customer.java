package pet.project.vistaapiemulator.model.entity;

import jakarta.persistence.*;
import lombok.*;
import pet.project.vistaapiemulator.model.common.Address;
import pet.project.vistaapiemulator.model.common.Contact;
import pet.project.vistaapiemulator.model.enums.CustomerStatus;

import java.math.BigDecimal;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Embedded
    private Contact contact;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private Boolean rebateAsCredit;

    private BigDecimal discount;

    private Long amountOfGallonsForLastTwoMonths;

    private String agreementLink;

    private Long currentMonthGallons;

    private Long forecastedFuelAmount;

    private BigDecimal forecastedRebate;

    private String paymentLink;
}
