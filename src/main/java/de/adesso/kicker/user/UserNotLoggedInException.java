package de.adesso.kicker.user;

public class UserNotLoggedInException extends RuntimeException {

    private static final long serialVersionUID = -3652376319061246641L;

    public UserNotLoggedInException() {
        super("User is not logged in");
    }
}
