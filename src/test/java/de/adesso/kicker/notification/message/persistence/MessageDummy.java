package de.adesso.kicker.notification.message.persistence;

import de.adesso.kicker.user.persistence.UserDummy;

public class MessageDummy {

    public static Message messageDeclined() {
        return new Message(UserDummy.defaultUser(), UserDummy.alternateUser(), MessageType.MESSAGE_DECLINED);
    }
}
