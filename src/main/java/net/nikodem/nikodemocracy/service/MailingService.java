package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.InvitationEmail;
import net.nikodem.nikodemocracy.model.dto.Mailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * @author Peter Nikodem
 */
@Service
public class MailingService {

    @Autowired
    private MailSender mailSender;

    //http://stackoverflow.com/questions/5289849/how-do-i-send-html-email-in-spring-mvc

    public void sendEmail(Mailable email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getTo());
        mail.setSubject(email.getSubject());
        mail.setText(email.getText());
        mailSender.send(mail);
    }
}
