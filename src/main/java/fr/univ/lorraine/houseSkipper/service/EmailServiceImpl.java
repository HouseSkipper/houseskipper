package fr.univ.lorraine.houseSkipper.service;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
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

    public void sendEmail() throws MailException {

        /*
         * This JavaMailSender Interface is used to send Mail in Spring Boot. This
         * JavaMailSender extends the MailSender Interface which contains send()
         * function. SimpleMailMessage Object is required because send() function uses
         * object of SimpleMailMessage as a Parameter
         */
        // EXAMPLE
        /*SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("simonhajek88@gmail.com");
        mail.setFrom("platform.houseskipper@gmail.com");
        mail.setSubject("Testing Mail API");
        mail.setText("Hurray ! You have done that dude...");
        */
        /*
         * This send() contains an Object of SimpleMailMessage as an Parameter
         */
        //javaMailSender.send(mail);
    }

    public void sendConfirmationEmail(ApplicationUser user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        helper.setTo(user.getUsername());
        helper.setSubject("Bienvenue sur Houseskipper");


        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"\n"+"Confirmez-votre inscription en cliquant sur le lien suivant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";


        message.setContent(html, "text/html");

        javaMailSender.send(message);
    }

    // TODO Ne pas oublier de générer un nouveau emailToken et de le repasser à invalid
    public void sendChangeConfirmationEmail(ApplicationUser user) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");

        helper.setTo(user.getUsername());
        helper.setSubject("Changement d'adresse email");


        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"\n"+"Veuillez confirmer votre nouvelle adresse email en cliquant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";


        message.setContent(html, "text/html");

        javaMailSender.send(message);
    }

}
