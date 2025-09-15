package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum PaymentType {
    PAYMENT("Payment"),
    DEPOSIT("Deposit"),
    REFUND("Refund"),
    REBATE("Rebate"),
    ACCOUNT_TOP_UP("Account Top-up");

    private final String value;

    PaymentType(String v) {
        this.value = v;
    }

    @JsonCreator
    public static PaymentType fromString(String value) {
        for (PaymentType type : PaymentType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
