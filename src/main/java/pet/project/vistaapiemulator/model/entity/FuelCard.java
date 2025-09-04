package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pet.project.vistaapiemulator.model.enums.FuelCardProvider;
import pet.project.vistaapiemulator.model.enums.FuelCardStatus;

@Entity
@Table(name = "fuel_cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelCard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;

    @Enumerated(EnumType.STRING)
    private FuelCardProvider provider;

    @Enumerated(EnumType.STRING)
    private FuelCardStatus status;

    private String pin;

    private String fullName;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private PurchaseOrder order;
}
