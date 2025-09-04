package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TransactionLineCategory {
    TRACTOR_FUEL("Tractor Fuel"),
    OTHER_FUEL("Other Fuel"),
    OTHER_PRODUCT("Other Product");

    private final String value;

    @JsonCreator
    public static TransactionLineCategory fromString(String value) {
        for (TransactionLineCategory category : TransactionLineCategory.values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    TransactionLineCategory(String v) {
        this.value = v;
    }
}
