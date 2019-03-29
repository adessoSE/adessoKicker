package de.adesso.kicker.notification.message.service;

import de.adesso.kicker.notification.message.persistence.Message;
import de.adesso.kicker.notification.message.persistence.MessageDummy;
import de.adesso.kicker.notification.message.persistence.MessageRepository;
import de.adesso.kicker.notification.message.persistence.MessageType;
import de.adesso.kicker.user.persistence.UserDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@TestPropertySource("classpath:application-test.properties")
class SendMessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private SendMessageService sendMessageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Save a decline Message")
    void saveDeclineMessage() {
        // given
        var sender = UserDummy.defaultUser();
        var receiver = UserDummy.alternateUser();

        // when
        sendMessageService.sendMessage(sender, receiver, MessageType.MESSAGE_DECLINED);

        // then
        then(messageRepository).should(times(1)).save(any(Message.class));
    }

    @Test
    void verifyMessageIsDeleted() {
        // given
        var message = MessageDummy.messageDeclined();
        willDoNothing().given(messageRepository).delete(message);

        // when
        sendMessageService.deleteMessage(message);

        // then
        then(messageRepository).should(times(1)).delete(message);
    }
}
