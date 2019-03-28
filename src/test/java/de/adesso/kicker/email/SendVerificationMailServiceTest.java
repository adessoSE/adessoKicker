package de.adesso.kicker.email;

import de.adesso.kicker.match.persistence.MatchDummy;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@TestPropertySource("classpath:application-test.properties")
class SendVerificationMailServiceTest {

    @Mock
    private JavaMailSenderImpl mailSender;

    @Mock
    private EmailMessageBuilder emailMessageBuilder;

    @InjectMocks
    private SendVerificationMailService sendVerificationMailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void emailShouldBeSent() {
        // given
        var match = MatchDummy.match();
        var matchVerificationSentEvent = mock(MatchVerificationSentEvent.class);
        var matchVerificationRequest = mock(MatchVerificationRequest.class);
        given(matchVerificationSentEvent.getMatchVerificationRequest()).willReturn(matchVerificationRequest);
        given(matchVerificationRequest.getMatch()).willReturn(match);
        given(matchVerificationRequest.getReceiver()).willReturn(match.getTeamAPlayer1());
        given(matchVerificationRequest.getReceiver()).willReturn(match.getTeamBPlayer1());
        given(emailMessageBuilder.build(any(), any())).willReturn("message");

        // when
        sendVerificationMailService.sendVerification(matchVerificationSentEvent);

        // then
        then(mailSender).should().send(any(MimeMessagePreparator.class));
    }
}
