package pet.project.vistaapiemulator.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alert {
    public static final String FINANCIAL_WARNING_LOW_BALANCE = "FINANCIAL.WARNING_LOW_BALANCE";
    public static final String FINANCIAL_CRITICAL_LOW_BALANCE = "FINANCIAL.CRITICAL_LOW_BALANCE";
    public static final String FINANCIAL_ACCOUNT_BLOCKED = "FINANCIAL.ACCOUNT_BLOCKED";
    public static final String FINANCIAL_ACCOUNT_UNBLOCKED = "FINANCIAL.ACCOUNT_UNBLOCKED";
    public static final String FINANCIAL_PAYMENT_CONFIRMATION = "FINANCIAL.PAYMENT_CONFIRMATION";

    public static final String FUEL_MID_MONTH_CHECK = "FUEL.MID_MONTH_CHECK";
    public static final String FUEL_MID_MONTH_CRITICAL = "FUEL.MID_MONTH_CRITICAL";
    public static final String FUEL_WEEK_3_CHECK = "FUEL.WEEK_3_CHECK";
    public static final String FUEL_MONTH_END_CHECK = "FUEL.MONTH_END_CHECK";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long date;
    private Long cardId;
    private String type;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public static final Set<String> ALERT_TYPES = Set.of(
            FINANCIAL_WARNING_LOW_BALANCE,
            FINANCIAL_CRITICAL_LOW_BALANCE,
            FINANCIAL_ACCOUNT_BLOCKED,
            FINANCIAL_ACCOUNT_UNBLOCKED,
            FINANCIAL_PAYMENT_CONFIRMATION,
            FUEL_MID_MONTH_CHECK,
            FUEL_MID_MONTH_CRITICAL,
            FUEL_WEEK_3_CHECK,
            FUEL_MONTH_END_CHECK
    );
}
