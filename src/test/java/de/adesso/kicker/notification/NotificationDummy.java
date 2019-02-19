package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserDummy;

public class NotificationDummy {

    private UserDummy userDummy = new UserDummy();

    public Notification notification() {
        return new Notification(userDummy.defaultUser(), userDummy.alternateUser(), "message");
    }

    public Notification notification_with_equal_sender_receiver() {
        return new Notification(userDummy.defaultUser(), userDummy.defaultUser(), "message");
    }

    public Notification notification_with_no_receiver() {
        return new Notification(userDummy.defaultUser(), null, "message");
    }

    public Notification notification_with_no_sender() {
        return new Notification(null, userDummy.defaultUser(), "message");
    }
}
