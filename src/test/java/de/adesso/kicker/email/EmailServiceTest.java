package de.adesso.kicker.email;

import de.adesso.kicker.events.match.MatchVerificationSentEvent;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.user.persistence.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSenderImpl mailSenderMock;

    @Mock
    private MatchVerificationSentEvent matchVerificationSentEventMock;

    @Mock
    private Match matchMock;

    @Mock
    private MatchVerificationRequest matchVerificationRequestMock;

    @Mock
    private User teamAPlayer1Mock;

    @Mock
    private User teamBPlayer1Mock;

    private EmailService emailService;

    @Before
    public void setUp() {
        this.emailService = new EmailService(mailSenderMock);
    }

    @Test
    public void when1v1thenSendEmail() throws NullPointerException {
        // given
        var matchId = "test-match-1";
        var notificationId = 1337L;
        var teamAPlayer1Mail = "teamAplayer1@test.com";
        var teamBPlayer1Mail = "teamBplayer1@test.com";
        var teamAPlayer1FullName = "Full Name PlayerA1";
        var teamBPlayer1FullName = "Full Name PlayerB1";
        var matchDate = LocalDate.now();
        var expectedAcceptUrl = EmailService.ACCEPT_URL + notificationId;
        var expectedDeclineUrl = EmailService.ACCEPT_URL + notificationId;
        var expectedWinnerText = String.format("Winners: %s\tLosers:%s",
                teamAPlayer1FullName, teamBPlayer1FullName);
        var expectedMessageSubject = String.format("Verify Match: %s played on %s",
                matchId, matchDate.toString());
        var expectedMessageText = String.format("Your recently played Match against %s needs to be verified.\n%s\nVerify -> %s\nDecline -> %s",
                teamAPlayer1FullName, expectedWinnerText, expectedAcceptUrl, expectedDeclineUrl);

        var expectedSimpleMailMessage = new SimpleMailMessage();
        expectedSimpleMailMessage.setFrom(teamAPlayer1Mail);
        expectedSimpleMailMessage.setTo(teamBPlayer1Mail);
        expectedSimpleMailMessage.setSubject(expectedMessageSubject);
        expectedSimpleMailMessage.setText(expectedMessageText);

        given(teamAPlayer1Mock.getEmail()).willReturn(teamAPlayer1Mail);
        given(teamAPlayer1Mock.getFullName()).willReturn(teamAPlayer1FullName);

        given(teamBPlayer1Mock.getEmail()).willReturn(teamBPlayer1Mail);
        given(teamBPlayer1Mock.getFullName()).willReturn(teamBPlayer1FullName);

        given(matchMock.getMatchId()).willReturn(matchId);
        given(matchMock.getDate()).willReturn(matchDate);
        given(matchMock.getWinners()).willReturn(List.of(teamAPlayer1Mock));
        given(matchMock.getLosers()).willReturn(List.of(teamBPlayer1Mock));

        given(matchVerificationSentEventMock.getMatchVerificationRequest()).willReturn(matchVerificationRequestMock);
        given(matchVerificationRequestMock.getMatch()).willReturn(matchMock);
        given(matchVerificationRequestMock.getReceiver()).willReturn(teamBPlayer1Mock);
        given(matchVerificationRequestMock.getNotificationId()).willReturn(notificationId);

        given(matchMock.getTeamAPlayer1()).willReturn(teamAPlayer1Mock);

        // when
        emailService.sendVerification(matchVerificationSentEventMock);

        // then
        verify(mailSenderMock).send(expectedSimpleMailMessage);
    }

    @Test
    public void when1v2thenSendEmail() {
        // given

        // when

        // then
    }

    @Test
    public void when2v1thenSendEmail() {
        // given

        // when

        // then
    }

    @Test
    public void when2v2thenSendEmail() {
        // given

        // when

        // then
    }

}
