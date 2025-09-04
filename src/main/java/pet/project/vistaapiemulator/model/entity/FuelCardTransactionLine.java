package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pet.project.vistaapiemulator.model.enums.TransactionLineCategory;
import pet.project.vistaapiemulator.model.enums.TransactionLineType;

import java.math.BigDecimal;

@Entity
@Table(name = "fuel_card_transaction_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelCardTransactionLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionLineType type;

    @Enumerated(EnumType.STRING)
    private TransactionLineCategory category;

    private BigDecimal quantity;

    private BigDecimal pricePerUnit;

    private BigDecimal tax;

    private BigDecimal discountAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    @JsonIgnore
    private FuelCardTransaction transaction;
}
