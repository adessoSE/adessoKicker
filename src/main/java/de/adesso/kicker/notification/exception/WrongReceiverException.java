package de.adesso.kicker.notification.exception;

public class WrongReceiverException extends RuntimeException {

    public WrongReceiverException(){ super("Logged in user does not match notification receiver"); }
}
