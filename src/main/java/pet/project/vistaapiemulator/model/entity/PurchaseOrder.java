package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pet.project.vistaapiemulator.model.common.Address;
import pet.project.vistaapiemulator.model.common.Contact;
import pet.project.vistaapiemulator.model.enums.OrderStatus;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String shippingMethod;

    private String trackingNumber;

    private Integer cardsQuantity;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Contact shippingContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}
