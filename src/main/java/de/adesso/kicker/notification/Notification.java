package de.adesso.kicker.notification;

import de.adesso.kicker.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public abstract class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User sender;
    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User receiver;

    private LocalDate sendDate;

    private NotificationType type;

    public Notification() {
    }

    public Notification(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.sendDate = LocalDate.now();
    }

    public long getNotificationId() {
        return notificationId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }
}
