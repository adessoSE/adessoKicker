package de.adesso.kicker.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = -7574273332388134702L;

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
