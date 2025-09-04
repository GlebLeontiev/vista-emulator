package pet.project.vistaapiemulator.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum FuelCardProvider {
    EFS("EFS"), Comdata("Comdata"), BVD_PETROLEUM("BVD Petroleum");

    private final String name;

    FuelCardProvider(String name) {
        this.name = name;
    }

    @JsonCreator
    public static FuelCardProvider fromString(String value) {
        for (FuelCardProvider provider : FuelCardProvider.values()) {
            if (provider.name.equalsIgnoreCase(value)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
