package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pet.project.vistaapiemulator.model.enums.Currency;
import pet.project.vistaapiemulator.model.enums.PaymentStatus;
import pet.project.vistaapiemulator.model.enums.PaymentType;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long datetime;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}
