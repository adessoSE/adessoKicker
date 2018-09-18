package de.adesso.adessoKicker.objects;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.util.Date;

@Entity
@Table(name = "tbNotification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationId;

    private String notificationType;

    private Date time;

    @ManyToOne(targetEntity = User.class)
    private User user;

    private String notification;

    public Notification() {

        User user = new User();
    }

    public Notification(String notificationType, Date time, User user) {
        this.notificationType = notificationType;
        this.time = time;
        this.user = user;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }
}
