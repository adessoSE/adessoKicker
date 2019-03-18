package de.adesso.kicker.email;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {

    static final String ACCEPT_URL = "http://localhost/notifications/accept/";

    static final String DECLINE_URL = "http://localhost/notifications/decline/";

    private final JavaMailSender mailSender;

    @EventListener
    public void sendVerification(MatchVerificationSentEvent matchVerificationSentEvent) {
        var matchVerificationRequest = matchVerificationSentEvent.getMatchVerificationRequest();
        var match = matchVerificationRequest.getMatch();

        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(match.getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(matchVerificationRequest.getReceiver().getEmail());
        simpleMailMessage.setSubject(setSubject(match));
        simpleMailMessage.setText(verificationText(matchVerificationSentEvent));

        mailSender.send(simpleMailMessage);
    }

    private String setSubject(Match match) {
        return String.format("Verify Match: %s played on %s", match.getMatchId(), match.getDate().toString());
    }

    private String verificationText(MatchVerificationSentEvent matchVerificationSentEvent) {
        String acceptUrl = ACCEPT_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();
        String declineUrl = DECLINE_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();

        Match match = matchVerificationSentEvent.getMatchVerificationRequest().getMatch();

        String playerA1Name = match.getTeamAPlayer1().getFullName();

        User playerA2 = match.getTeamAPlayer2();

        String winnerText = getWinner(match);

        if (checkPlayerExist(playerA2)) {
            String playerA2Name = match.getTeamAPlayer2().getFullName();
            return String.format(
                    "Your recently played Match against %s and %s needs to be verified.\n%s\nVerify -> %s\nDecline -> %s",
                    playerA1Name, playerA2Name, winnerText, acceptUrl, declineUrl);
        } else {
            return String.format(
                    "Your recently played Match against %s needs to be verified.\n%s\nVerify -> %s\nDecline -> %s",
                    playerA1Name, winnerText, acceptUrl, declineUrl);
        }
    }

    private boolean checkPlayerExist(User user) {
        return Objects.nonNull(user);
    }

    private String getWinner(Match match) {
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
