package friends.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * EmptyException
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmptyException extends RuntimeException {
    public EmptyException(String message) {
        super(message);
    }
}
