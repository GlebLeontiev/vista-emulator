package pet.project.vistaapiemulator.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@StandardException
public class UnauthorizedException extends RuntimeException {
}
