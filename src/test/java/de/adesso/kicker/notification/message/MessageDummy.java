package de.adesso.kicker.notification.message;

import de.adesso.kicker.notification.message.persistence.Message;
import de.adesso.kicker.notification.message.persistence.MessageType;
import de.adesso.kicker.user.UserDummy;

public class MessageDummy {

    public static Message messageDeclined() {
        return new Message(UserDummy.defaultUser(), UserDummy.alternateUser(), MessageType.MESSAGE_DECLINED);
    }
}
