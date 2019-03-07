package de.adesso.kicker.notification.message.persistence;

import de.adesso.kicker.notification.persistence.Notification;
import de.adesso.kicker.notification.persistence.NotificationType;
import de.adesso.kicker.user.persistence.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Message extends Notification {

    private MessageType messageType;

    public Message(User sender, User receiver, MessageType messageType) {
        super(sender, receiver);
        super.setType(NotificationType.MESSAGE);
        this.messageType = messageType;
    }
}
