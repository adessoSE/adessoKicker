package de.adesso.kicker.notification;

import de.adesso.kicker.user.UserDummy;

class NotificationDummy {

    private UserDummy userDummy = new UserDummy();

    Notification defaultNotification() {
        return new Notification(userDummy.alternateUser(), userDummy.defaultUser());
    }

    Notification alternateNotification() {
        return new Notification(userDummy.alternateUser2(), userDummy.alternateUser1());
    }

    Notification alternateNotification1() {
        return new Notification(userDummy.alternateUser3(), userDummy.alternateUser2());
    }

    Notification alternateNotification2() {
        return new Notification(userDummy.alternateUser4(), userDummy.alternateUser3());
    }

    Notification nullNotification() {
        return null;
    }
}
