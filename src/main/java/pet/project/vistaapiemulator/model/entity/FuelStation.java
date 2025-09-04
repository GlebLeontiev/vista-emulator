package pet.project.vistaapiemulator.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fuel_stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String latitude;
    private String longitude;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
}
