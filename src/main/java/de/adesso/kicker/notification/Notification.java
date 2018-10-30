package de.adesso.kicker.notification;

import de.adesso.kicker.user.User;

import java.util.Date;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long notificationId;

    @ManyToOne(targetEntity = User.class)
    protected User receiver;

    @ManyToOne(targetEntity = User.class)
    protected User sender;

    protected Date sendDate;

    protected String message;

    public Notification() {
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

    @Override
    public String toString() {
        return "Notification{" + "notificationId=" + notificationId + ", notificationType=" + ", receiver=" + receiver
                + ", sender=" + sender + ", sendDate=" + sendDate + ", message=" + message;

    }
}
