package de.adesso.kicker.email;

import de.adesso.kicker.events.match.MatchVerificationSentEvent;
import de.adesso.kicker.match.service.MatchService;
import de.adesso.kicker.user.UserDummy;
import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @MockBean
    MatchService matchService;

    @MockBean
    UserService userService;

    @MockBean
    EmailService emailService;

    @MockBean
    JavaMailSenderImpl javaMailSender;

    @Autowired
    private MockMvc mockMvc;

    MatchVerificationSentEvent matchVerificationSentEvent;

    private static User createPlayer() {
        return UserDummy.defaultUser();
    }

    @Test
    public void when1v1thenSendEmail() throws NullPointerException {
        // given
        var playerA = createPlayer();
        var playerB = createPlayer();

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer1(playerA);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer1(playerB);

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer2(null);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer2(null);

        var realPlayerA = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamAPlayer1();
        var realPlayerB = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamBPlayer1();

        var expectedFrom = realPlayerA.getEmail();
        var expectedTo = realPlayerB.getEmail();
        var expectedSubject = emailService.setSubject(matchVerificationSentEvent.getMatchVerificationRequest().getMatch());
        var expectedText = emailService.verificationText(matchVerificationSentEvent);

        var expectedSimpleMailMessage = new SimpleMailMessage();

        expectedSimpleMailMessage.setFrom(expectedFrom);
        expectedSimpleMailMessage.setTo(expectedTo);
        expectedSimpleMailMessage.setSubject(expectedSubject);
        expectedSimpleMailMessage.setText(expectedText);

        // when
        emailService.sendVerification(matchVerificationSentEvent);

        // then
        verify(times(1));
        verify(javaMailSender).send(expectedSimpleMailMessage);
    }

    @Test
    public void when1v2thenSendEmail() {
        // given
        // given
        var playerA = createPlayer();
        var playerB1 = createPlayer();
        var playerB2 = createPlayer();

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer1(playerA);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer1(playerB1);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer2(playerB2);

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer2(null);

        var realPlayerA = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamAPlayer1();
        var realPlayerB = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamBPlayer1();

        var expectedFrom = realPlayerA.getEmail();
        var expectedTo = realPlayerB.getEmail(); //HIER NOCH ÄNDERN
        var expectedSubject = emailService.setSubject(matchVerificationSentEvent.getMatchVerificationRequest().getMatch());
        var expectedText = emailService.verificationText(matchVerificationSentEvent);

        var expectedSimpleMailMessage = new SimpleMailMessage();

        expectedSimpleMailMessage.setFrom(expectedFrom);
        expectedSimpleMailMessage.setTo(expectedTo);
        expectedSimpleMailMessage.setSubject(expectedSubject);
        expectedSimpleMailMessage.setText(expectedText);

        // when
        emailService.sendVerification(matchVerificationSentEvent);

        // then
        verify(times(1));
        verify(javaMailSender).send(expectedSimpleMailMessage);

    }

    @Test
    public void when2v1thenSendEmail() {
        // given
        var playerA1 = createPlayer();
        var playerA2 = createPlayer();
        var playerB1 = createPlayer();

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer1(playerA1);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer2(playerA2);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer1(playerB1);

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer2(null);

        var realPlayerA1 = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamAPlayer1();
        var realPlayerB1 = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamBPlayer1();

        var expectedFrom = realPlayerA1.getEmail();
        var expectedTo = realPlayerB1.getEmail();
        var expectedSubject = emailService.setSubject(matchVerificationSentEvent.getMatchVerificationRequest().getMatch());
        var expectedText = emailService.verificationText(matchVerificationSentEvent);

        var expectedSimpleMailMessage = new SimpleMailMessage();

        expectedSimpleMailMessage.setFrom(expectedFrom);
        expectedSimpleMailMessage.setTo(expectedTo);
        expectedSimpleMailMessage.setSubject(expectedSubject);
        expectedSimpleMailMessage.setText(expectedText);

        // when
        emailService.sendVerification(matchVerificationSentEvent);

        // then
        verify(times(1));
        verify(javaMailSender).send(expectedSimpleMailMessage);
    }

    @Test
    public void when2v2thenSendEmail() {
        // given
        var playerA1 = createPlayer();
        var playerA2 = createPlayer();
        var playerB1 = createPlayer();
        var playerB2 = createPlayer();

        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer1(playerA1);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamAPlayer2(playerA2);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer1(playerB1);
        matchVerificationSentEvent.getMatchVerificationRequest().getMatch().setTeamBPlayer2(playerB2);

        var realPlayerA = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamAPlayer1();
        var realPlayerB1 = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamBPlayer1();
        var realPlayerB2 = matchVerificationSentEvent.getMatchVerificationRequest().getMatch().getTeamBPlayer2();

        var expectedFrom = realPlayerA.getEmail();
        var expectedTo = realPlayerB2.getEmail(); //noch ka
        var expectedSubject = emailService.setSubject(matchVerificationSentEvent.getMatchVerificationRequest().getMatch());
        var expectedText = emailService.verificationText(matchVerificationSentEvent);

        var expectedSimpleMailMessage = new SimpleMailMessage();

        expectedSimpleMailMessage.setFrom(expectedFrom);
        expectedSimpleMailMessage.setTo(expectedTo);
        expectedSimpleMailMessage.setSubject(expectedSubject);
        expectedSimpleMailMessage.setText(expectedText);

        // when
        emailService.sendVerification(matchVerificationSentEvent);

        // then
        verify(times(2));
        verify(javaMailSender).send(expectedSimpleMailMessage);
    }
}
