package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "fuel_card_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelCardTransaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refTransactionId;
    private Long datetime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("cardId")
    private FuelCard card;

    private BigDecimal totalAmount;

    private BigDecimal tax;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "station_id")
    private FuelStation station;

    @OneToMany(mappedBy = "transaction",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuelCardTransactionLine> lines;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
}
