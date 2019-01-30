package de.adesso.kicker.notification;

import de.adesso.kicker.notification.matchverificationrequest.MatchVerificationRequest;
import de.adesso.kicker.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notificationId;

    @ManyToOne(targetEntity = User.class)
    private User receiver;

    @ManyToOne(targetEntity = User.class)
    private User sender;

    private NotificationType type;

    private Date sendDate;

    private String message;

    public Notification() {

        type = NotificationType.Notification;
    }

    public Notification(User sender, User receiver, String message) {

        this();
        this.sendDate = new Date();
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", notificationType=" + ", receiver=" + receiver
                + ", sender=" + sender + ", sendDate=" + sendDate + ", message=" + message;

    }
}
