package planplus.core.services.exceptions;

/**
 * Created by Syed Asif Iqbal
 */
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExistsException(String message) {
        super(message);
    }

    public AccountExistsException() {
        super();
    }
}
