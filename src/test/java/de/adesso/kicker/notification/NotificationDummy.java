package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserDummy;

public class NotificationDummy {

    private UserDummy userDummy = new UserDummy();

    public Notification defaultNotification() { return new Notification("Test-Message 1", userDummy.defaultUser(), userDummy.alternateUser());
    }

    public Notification alternateNotification() {
        return new Notification("Test-Message 2", userDummy.alternateUser1(), userDummy.alternateUser2());
    }

    public Notification alternateNotification1() {
        return new Notification("Test-Message 3", userDummy.alternateUser2(), userDummy.alternateUser3());
    }

    public Notification alternateNotification2() {
        return new Notification("Test-Message 4", userDummy.alternateUser3(), userDummy.alternateUser4());
    }

    public Notification nullNotification() {
        return null;
    }
}
