package de.adesso.kicker.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class WrongReceiverException extends RuntimeException {
    public WrongReceiverException() {
        super("Logged in user does not match notification receiver");
    }
}
