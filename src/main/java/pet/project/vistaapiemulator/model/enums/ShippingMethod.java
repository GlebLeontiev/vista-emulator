package pet.project.vistaapiemulator.model.enums;

import lombok.Getter;

@Getter
public enum ShippingMethod {
    UPS("UPS"),
    FEDEX("FedEx"),
    USPS("USPS");

    private final String value;

    ShippingMethod(String v) {
        this.value = v;
    }
}
