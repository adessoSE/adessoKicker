package de.adesso.kicker.email;

import de.adesso.kicker.configurations.EmailConfig;
import de.adesso.kicker.events.match.MatchCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Properties;

@Service
public class EmailService {

    private EmailConfig emailConfig;

    private static Properties properties = new Properties();

    private static SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    @Autowired
    public EmailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @EventListener
    public void sendRequest(MatchCreatedEvent matchCreatedEvent) {
        JavaMailSenderImpl mailSender = emailConfig.initializeMailServer();
        simpleMailMessage.setFrom(matchCreatedEvent.getMatch().getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(matchCreatedEvent.getMatch().getTeamBPlayer1().getEmail());
        simpleMailMessage.setSubject(setSubject(matchCreatedEvent));
        simpleMailMessage.setText(requestText(matchCreatedEvent));
        mailSender.send(simpleMailMessage);
    }

    @EventListener
    public void sendVerification(MatchCreatedEvent matchCreatedEvent) {
        JavaMailSenderImpl mailSender = emailConfig.initializeMailServer();
        simpleMailMessage.setFrom(matchCreatedEvent.getMatch().getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(matchCreatedEvent.getMatch().getTeamBPlayer1().getEmail());
        //simpleMailMessage.setSubject(subject(matchCreatedEvent));
        //simpleMailMessage.setText(verificationText(matchCreatedEvent));
        simpleMailMessage.setSubject("hallo");
        simpleMailMessage.setText("hallo");
        mailSender.send(simpleMailMessage);
    }


    public String setSubject(MatchCreatedEvent matchCreatedEvent) {
        return String.format("Match: %s from %s", matchCreatedEvent.getMatch().getMatchId(), matchCreatedEvent.getMatch().getDate().toString());
    }

    public String requestText(MatchCreatedEvent matchCreatedEvent) {
        return String.format("You've sent %s a verification request.", matchCreatedEvent.getMatch().getTeamBPlayer1().getFirstName()+" "+matchCreatedEvent.getMatch().getTeamBPlayer1().getLastName())
    }

    public String verificationText(MatchCreatedEvent matchCreatedEvent) {
        return String.format(properties.getProperty("emailService.sendVerification.text"),
                matchCreatedEvent.getMatch().getTeamAPlayer1(),
                "localhost/notifications/accept/1",
                "localhost/notifications/decline/1");

    }

}