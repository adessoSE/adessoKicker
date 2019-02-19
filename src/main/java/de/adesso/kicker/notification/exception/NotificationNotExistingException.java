package de.adesso.kicker.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NotificationNotExistingException extends RuntimeException {

    public NotificationNotExistingException(long id) {
        super("There is no notification with id: " + id);
    }
}
