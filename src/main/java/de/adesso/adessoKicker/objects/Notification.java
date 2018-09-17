package de.adesso.adessoKicker.objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationId;

    private String notificationType;

    private Datetime time;

    private User user;

    public Notification() {

        User user = new User();
    }

    public Notification(long notificationId, String notificationType, Datetime time, User user) {
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

    public void setTime(Datetime time) {
        this.time = time;
    }

    public Datetime getTime() {
        return time;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
