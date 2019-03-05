package de.adesso.kicker.email;

import de.adesso.kicker.configurations.EmailConfig;
import de.adesso.kicker.events.match.MatchVerificationSentEvent;
import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.user.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class EmailService {

    private EmailConfig emailConfig;

    private final String ACCEPT_URL = "http://localhost/notifications/accept/";
    private final String DECLINE_URL = "http://localhost/notifications/decline/";

    private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    @Autowired
    public EmailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @EventListener
    public void sendVerification(MatchVerificationSentEvent matchVerificationSentEvent) {
        Match match = matchVerificationSentEvent.getMatchVerificationRequest().getMatch();

        JavaMailSenderImpl mailSender = emailConfig.setMailServerConfig();

        simpleMailMessage.setFrom(match.getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(matchVerificationSentEvent.getMatchVerificationRequest().getReceiver().getEmail());
        simpleMailMessage.setSubject(setSubject(match));
        simpleMailMessage.setText(verificationText(matchVerificationSentEvent));
        mailSender.send(simpleMailMessage);
    }

    public String setSubject(Match match) {
        return String.format("Verify Match: %s played on %s", match.getMatchId(), match.getDate().toString());
    }

    public String verificationText(MatchVerificationSentEvent matchVerificationSentEvent) {
        String acceptUrl = ACCEPT_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();
        String declineUrl = DECLINE_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();

        Match match = matchVerificationSentEvent.getMatchVerificationRequest().getMatch();

        String playerA1 = match.getTeamAPlayer1().getFullName();

        User userA2 = match.getTeamAPlayer2();

        String winnerText = getWinner(match);

        if (checkPlayerExist(userA2)) {
            String playerA2 = match.getTeamAPlayer2().getFullName();
            return String.format(
                    "Your recently played Match against %s and %s needs to be verified.\n%s\nVerify -> %s\nDecline -> %s",
                    playerA1, playerA2, winnerText, acceptUrl, declineUrl);
        } else {
            return String.format(
                    "Your recently played Match against %s needs to be verified.\n%s\nVerify -> %s\nDecline -> %s",
                    playerA1, winnerText, acceptUrl, declineUrl);
        }
    }

    public boolean checkPlayerExist(User user) {
        return !Objects.isNull(user);
    }

    public String getWinner(Match match) {
        ArrayList<String> winners = new ArrayList<>();
        for (User winner : match.getWinners()) {
            winners.add(winner.getFullName());
        }
        ArrayList<String> losers = new ArrayList<>();
        for (User loser : match.getLosers()) {
            losers.add(loser.getFullName());
        }
        return String.format("Winners: %s\tLosers:%s", winners, losers);
    }
}
