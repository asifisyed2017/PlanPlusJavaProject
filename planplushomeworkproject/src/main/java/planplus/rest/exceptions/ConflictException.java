package planplus.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Syed Asif Iqbal
 */
@ResponseStatus(value= HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException() {
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
}
