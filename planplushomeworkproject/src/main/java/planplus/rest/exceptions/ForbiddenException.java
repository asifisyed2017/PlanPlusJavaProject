package planplus.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Syed Asif Iqbal
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
}
