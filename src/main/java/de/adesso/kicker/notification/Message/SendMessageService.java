package de.adesso.kicker.notification.Message;

import de.adesso.kicker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private MessageRepository messageRepository;

    @Autowired
    public SendMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(User sender, User receiver, MessageType messageType) {
        saveMessage(new Message(sender, receiver, messageType));
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
