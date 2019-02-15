package de.adesso.kicker.email;

import de.adesso.kicker.configurations.EmailConfig;
import de.adesso.kicker.match.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private EmailConfig emailConfig;

    @Autowired
    public EmailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    public void sendRequest(Match match) {
        JavaMailSenderImpl mailSender = emailConfig.initializeMailServer();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(match.getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(match.getTeamBPlayer1().getEmail());
        simpleMailMessage.setSubject("Match: " + match.getMatchId() + "from " + match.getDate());
        simpleMailMessage.setText("You've sent " + match.getTeamBPlayer1().getFirstName() + ", "
                + match.getTeamBPlayer1().getLastName() + "a verification request.");
        mailSender.send(simpleMailMessage);
    }

    public void sendVerification(Match match) {

        JavaMailSenderImpl mailSender = emailConfig.initializeMailServer();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(match.getTeamAPlayer1().getEmail());
        simpleMailMessage.setTo(match.getTeamBPlayer1().getEmail());
        simpleMailMessage.setSubject("Match: " + match.getMatchId() + "from " + match.getDate());
        simpleMailMessage.setText("Your recently played Match against " + match.getTeamAPlayer1().getFirstName() + ", "
                + match.getTeamAPlayer1().getLastName() + " needs to be verified. " + match.getWinnerTeamA()
                + " won. Click here to verify or here to decline.");
        mailSender.send(simpleMailMessage);
    }

}
