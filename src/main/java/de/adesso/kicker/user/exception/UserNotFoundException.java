package de.adesso.kicker.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3312722333453690910L;

    public UserNotFoundException() {
        super("User does not exist");
    }
}
