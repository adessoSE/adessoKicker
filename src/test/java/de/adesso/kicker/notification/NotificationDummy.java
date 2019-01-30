package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserDummy;

class NotificationDummy {

    private UserDummy userDummy = new UserDummy();

    Notification defaultNotification() {
        return new Notification(userDummy.alternateUser(), userDummy.defaultUser(), "Test-Message 1");
    }

    Notification alternateNotification() {
        return new Notification(userDummy.alternateUser2(), userDummy.alternateUser1(), "Test-Message 2");
    }

    Notification alternateNotification1() {
        return new Notification(userDummy.alternateUser3(), userDummy.alternateUser2(), "Test-Message 3");
    }

    Notification alternateNotification2() {
        return new Notification(userDummy.alternateUser4(), userDummy.alternateUser3(), "Test-Message 4");
    }

    Notification nullNotification() {
        return null;
    }
}
