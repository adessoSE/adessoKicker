package de.adesso.kicker.notification.Message;

import de.adesso.kicker.user.UserDummy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SendMessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private SendMessageService sendMessageService;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Save a decline Message")
    void saveDeclineMessage() {
        // given
        // when
        sendMessageService.sendMessage(UserDummy.defaultUser(), UserDummy.alternateUser(),
                MessageType.MESSAGE_DECLINED);

        // then
        verify(messageRepository, times(1)).save(any(Message.class));
    }
}
