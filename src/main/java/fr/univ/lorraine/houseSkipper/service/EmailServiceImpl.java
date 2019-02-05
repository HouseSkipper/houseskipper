package fr.univ.lorraine.houseSkipper.service;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl{

    private final JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.javaMailSender = emailSender;
    }

    private void sendHtmlEmail(String subject, String htmlContent, String toEmail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        helper.setTo(toEmail);
        helper.setSubject(subject);

        message.setContent(htmlContent, "text/html");
        javaMailSender.send(message);
    }

    /*
    public void sendConfirmationEmail(ApplicationUser user) throws MessagingException {
        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"\n"+"Confirmez-votre inscription en cliquant sur le lien suivant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";

        sendHtmlEmail("Bienvenue sur Houseskipper", html, user.getUsername());
    }
    */

    public void sendConfirmationEmail(ApplicationUser user) throws MessagingException {
        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"\n"+"Confirmez-votre inscription en entrant le code suivant : <strong>" + user.getEmailToken() +"</strong></html>";

        sendHtmlEmail("Bienvenue sur Houseskipper", html, user.getUsername());
    }

    // TODO Ne pas oublier de générer un nouveau emailToken et de le repasser à invalid
    public void sendChangeConfirmationEmail(ApplicationUser user) throws MessagingException {
        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"\n"+"Veuillez confirmer votre nouvelle adresse email en cliquant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";

        sendHtmlEmail("Changement d'adresse email", html, user.getUsername());
    }

}
