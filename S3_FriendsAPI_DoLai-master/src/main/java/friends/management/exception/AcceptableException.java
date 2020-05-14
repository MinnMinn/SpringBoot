package friends.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AcceptableException
 */
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class AcceptableException extends RuntimeException {
    public AcceptableException(String message) {
        super(message);
    }
}
