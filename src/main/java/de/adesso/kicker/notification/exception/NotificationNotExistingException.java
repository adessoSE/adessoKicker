package de.adesso.kicker.notification.exception;

public class NotificationNotExistingException extends RuntimeException {

    public NotificationNotExistingException(long id) {
        super("There is no notification with id: " + id);
    }
}
