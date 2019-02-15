package de.adesso.kicker.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User does not exist");
    }
}
