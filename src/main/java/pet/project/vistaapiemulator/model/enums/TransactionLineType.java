package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TransactionLineType {
    FUEL("Fuel"),
    NON_FUEL("Non Fuel");

    private final String value;

    @JsonCreator
    public static TransactionLineType fromString(String value) {
        for (TransactionLineType type : TransactionLineType.values()) {
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

    TransactionLineType(String v) {
        this.value = v;
    }
}
