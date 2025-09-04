package pet.project.vistaapiemulator.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignCardRequest {
    @NotBlank
    private String fullName;

    @Email
    private String email;
}
