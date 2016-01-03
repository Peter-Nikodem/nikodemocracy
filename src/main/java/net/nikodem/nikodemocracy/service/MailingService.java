package net.nikodem.nikodemocracy.service;

import net.nikodem.nikodemocracy.model.dto.InvitationEmail;
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
    MailSender mailSender;


    public void sendEmail(InvitationEmail invitationEmail) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(invitationEmail.getTo());
        mail.setSubject(invitationEmail.getSubject());
        mail.setText(invitationEmail.getText());
        mailSender.send(mail);
    }
}
