package de.adesso.kicker.user.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("User is not logged in");
    }
}
