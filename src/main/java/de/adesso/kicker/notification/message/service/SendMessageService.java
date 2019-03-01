package de.adesso.kicker.notification.message.service;

import de.adesso.kicker.notification.message.persistence.Message;
import de.adesso.kicker.notification.message.persistence.MessageRepository;
import de.adesso.kicker.notification.message.persistence.MessageType;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    private final MessageRepository messageRepository;

    public void sendMessage(User sender, User receiver, MessageType messageType) {
        saveMessage(new Message(sender, receiver, messageType));
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    private void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
