package de.adesso.kicker.match.exception;

import org.springframework.mail.MailSendException;

public class TooManyMailsException extends MailSendException {
    public TooManyMailsException() {
        super("Too many mail requests at once");
    }
}
