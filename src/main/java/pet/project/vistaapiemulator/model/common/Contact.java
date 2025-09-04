package pet.project.vistaapiemulator.model.common;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private String contactName;
    private String email;
    private String phone;
}
