package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CustomerStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private final String value;

    CustomerStatus(String v) {
        this.value = v;
    }

    @JsonCreator
    public static CustomerStatus fromString(String value) {
        for (CustomerStatus status : CustomerStatus.values()) {
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
