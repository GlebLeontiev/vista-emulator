package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FuelCardStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DELETED("Deleted");

    private final String value;

    FuelCardStatus(String v) {
        this.value = v;
    }

    @JsonCreator
    public static FuelCardStatus fromString(String value) {
        for (FuelCardStatus status : FuelCardStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
