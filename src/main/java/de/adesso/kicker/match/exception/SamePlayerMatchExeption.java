package de.adesso.kicker.match.exception;

public class SamePlayerMatchExeption extends RuntimeException {
    public SamePlayerMatchExeption() {
        super("Players can't play against themselves");
    }
}
