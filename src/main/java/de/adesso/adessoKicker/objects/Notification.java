package de.adesso.adessoKicker.objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationId;

    private String notificationType;

    private Date time;

    private User user;

    private String notification;

    public Notification() {

        User user = new User();
    }

    public Notification(long notificationId, String notificationType, Date time, User user) {
        this.notificationId = notificationId;
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
