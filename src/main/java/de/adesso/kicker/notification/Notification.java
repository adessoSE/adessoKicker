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

    public Notification() {

    }

    public Notification(User sender, User receiver) {
        this.type = NotificationType.NOTIFICATION;
        this.receiver = receiver;
        this.sender = sender;
        this.sendDate = new Date();
    }

    public String generateMessage() {
        return "";
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

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", notificationType=" + ", receiver=" + receiver
                + ", sender=" + sender + ", sendDate=" + sendDate;

    }
}
