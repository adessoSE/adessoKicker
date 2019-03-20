package de.adesso.kicker.email;

import de.adesso.kicker.match.persistence.Match;
import de.adesso.kicker.notification.matchverificationrequest.persistence.MatchVerificationRequest;
import de.adesso.kicker.notification.matchverificationrequest.service.events.MatchVerificationSentEvent;
import de.adesso.kicker.user.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class SendVerificationMailService {

    private final JavaMailSender mailSender;

    private final EmailMessageBuilder emailMessageBuilder;

    @EventListener
    public void sendVerification(MatchVerificationSentEvent matchVerificationSentEvent) {
        User user = matchVerificationSentEvent.getMatchVerificationRequest().getReceiver();

        if (checkSendMail(user)) {
            var message = createEmail(matchVerificationSentEvent);
            mailSender.send(message);
        }
    }

    private String setSubject(Match match) {
        var labels = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        var date = match.getDate().format(LocalDateFormatter());
        return MessageFormat.format(labels.getString("email.subject"), match.getMatchId(), date);
    }

    private String verificationText(MatchVerificationSentEvent matchVerificationSentEvent) {
        var matchVerificationRequest = matchVerificationSentEvent.getMatchVerificationRequest();

        return emailMessageBuilder.build(setProperties(matchVerificationRequest),
                "email/plainText.txt");
    }

    private String verificationHTML(MatchVerificationSentEvent matchVerificationSentEvent) {
        var matchVerificationRequest = matchVerificationSentEvent.getMatchVerificationRequest();

        return emailMessageBuilder.build(setProperties(matchVerificationRequest),
                "email/verification.html");
    }

    private HashMap<String, Object> setProperties(MatchVerificationRequest matchVerificationRequest) {
        var match = matchVerificationRequest.getMatch();

        var mailProperties = new HashMap<String, Object>();

        mailProperties.put("matchId", match.getMatchId());
        mailProperties.put("receiver", matchVerificationRequest.getReceiver().getFullName());
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

    private boolean checkSendMail(User user) { return user.isSendEmail();}

    private DateTimeFormatter LocalDateFormatter() {
        var dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        return dateTimeFormatter;
    }

    private MimeMessagePreparator createEmail(MatchVerificationSentEvent matchVerificationSentEvent) {
        var matchVerificationRequest = matchVerificationSentEvent.getMatchVerificationRequest();
        var match = matchVerificationRequest.getMatch();
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(match.getTeamAPlayer1().getEmail());
            messageHelper.setTo(matchVerificationRequest.getReceiver().getEmail());
            messageHelper.setSubject(setSubject(match));
            messageHelper.setText(verificationText(matchVerificationSentEvent),
                    verificationHTML(matchVerificationSentEvent));
        };
    }
}
