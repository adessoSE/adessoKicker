package de.adesso.kicker.notification.Message;

import de.adesso.kicker.notification.Notification;
import de.adesso.kicker.notification.NotificationType;
import de.adesso.kicker.user.User;

import javax.persistence.Entity;

@Entity
public class Message extends Notification {

    private MessageType messageType;

    public Message() {
    }

    public Message(User sender, User receiver, MessageType messageType) {
        super(sender, receiver);
        super.setType(NotificationType.MESSAGE);
        this.messageType = messageType;
    }
}
