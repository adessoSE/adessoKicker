package de.adesso.kicker.email;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final EmailMessageBuilder emailMessageBuilder;

    static final String ACCEPT_URL = "http://localhost/notifications/accept/";

    static final String DECLINE_URL = "http://localhost/notifications/decline/";

    @EventListener
    public void sendVerification(MatchVerificationSentEvent matchVerificationSentEvent) {
        var matchVerificationRequest = matchVerificationSentEvent.getMatchVerificationRequest();
        var match = matchVerificationRequest.getMatch();
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(match.getTeamAPlayer1().getEmail());
            messageHelper.setTo(matchVerificationRequest.getReceiver().getEmail());
            messageHelper.setSubject(setSubject(match));
            messageHelper.setText(verificationText(matchVerificationSentEvent), true);
        };
        mailSender.send(messagePreparator);
    }

    private String setSubject(Match match) {
        return String.format("Verify Match: %s played on %s", match.getMatchId(), match.getDate().toString());
    }

    private String verificationText(MatchVerificationSentEvent matchVerificationSentEvent) {
        String acceptUrl = ACCEPT_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();
        String declineUrl = DECLINE_URL + matchVerificationSentEvent.getMatchVerificationRequest().getNotificationId();

        Match match = matchVerificationSentEvent.getMatchVerificationRequest().getMatch();

        return emailMessageBuilder.build(setProperties(match, acceptUrl, declineUrl), "email/verification.html");
    }

    private HashMap<String, Object> setProperties(Match match, String accept, String decline) {
        var mailProperties = new HashMap<String, Object>();
        mailProperties.put("acceptUrl", accept);
        mailProperties.put("declineUrl", decline);

        mailProperties.put("playerA1Name", match.getTeamAPlayer1().getFullName());
        mailProperties.put("playerB1Name", match.getTeamBPlayer1().getFullName());

        if (checkPlayerExist(match.getTeamAPlayer2())) {
            mailProperties.put("playerA2Name", match.getTeamAPlayer2().getFullName());
        }
        if (checkPlayerExist(match.getTeamBPlayer2())) {
            mailProperties.put("playerB2Name", match.getTeamBPlayer2().getFullName());
        }

        mailProperties.put("winners", match.getWinners());
        mailProperties.put("losers", match.getLosers());

        return mailProperties;
    }

    private boolean checkPlayerExist(User user) {
        return Objects.nonNull(user);
    }
}
