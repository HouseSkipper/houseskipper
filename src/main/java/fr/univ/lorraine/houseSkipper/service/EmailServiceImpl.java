package fr.univ.lorraine.houseSkipper.service;

import fr.univ.lorraine.houseSkipper.model.ApplicationUser;

import fr.univ.lorraine.houseSkipper.model.Prestataire;
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
        String html = "<html>Bonjour "+user.getFirstname()+"<br>"+"Confirmez-votre inscription en cliquant sur le lien suivant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";

        sendHtmlEmail("Bienvenue sur Houseskipper", html, user.getUsername());
    }
    */

    public void sendConfirmationEmail(ApplicationUser user) throws MessagingException {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("Bonjour " + user.getFirstname() + " " + user.getLastname() + "<br>");
        html.append("<br>");
        html.append("Vous avez demandé la création d'un compte d'accès à la plateforme HouseSkipper." + "<br>");
        html.append("<br>");
        html.append("Afin de finaliser votre opération, veuillez utiliser le code de sécurité suivant lorsqu'il vous sera demandé." + "<br>");
        html.append("Code de sécurité : <strong>" + user.getEmailToken() + "</strong><br>");
        html.append("<br>");
        html.append("Si vous n'avez pas effectué cette opération, nous vous invitons à nous faire parvenir un message à contact.donneesprivées@houseskipper.com." + "<br>");
        html.append("<br><br>");
        html.append("Cordialement," + "<br>");
        html.append("<br>");
        html.append("L'assistance HouseSkipper </html>");
        sendHtmlEmail("Bienvenue sur Houseskipper", html.toString(), user.getUsername());
    }

    public void sendConfirmationEmail(Prestataire prestataire) throws MessagingException {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("Bonjour " + prestataire.getNom()  + "<br>");
        html.append("<br>");
        html.append("Vous avez demandé la création d'un compte d'accès à la plateforme HouseSkipper." + "<br>");
        html.append("<br>");
        html.append("Afin de vous connectez, veuillez utiliser le code de sécurité suivant." + "<br>");
        html.append("Code de sécurité : <strong>" + prestataire.getPassword() + "</strong><br>");
        html.append("<br>");
        html.append("Si vous n'avez pas effectué cette opération, nous vous invitons à nous faire parvenir un message à contact.donneesprivées@houseskipper.com." + "<br>");
        html.append("<br><br>");
        html.append("Cordialement," + "<br>");
        html.append("<br>");
        html.append("L'assistance HouseSkipper </html>");
        sendHtmlEmail("Bienvenue sur Houseskipper", html.toString(), prestataire.getEmail());
    }

    public void sendCheckUser(ApplicationUser user) throws MessagingException {
        StringBuilder html = new StringBuilder();
        html.append("<html>");
        html.append("Bonjour " + user.getFirstname() + " " + user.getLastname() + "<br>");
        html.append("<br>");
        html.append("Vos identifiants de connexion à HouseSkipper ont été utilisé pour se connecter à la plateforme partir d'un nouvel appareil." + "<br>");
        html.append("<br>");
        html.append("Afin de finaliser votre opération, veuillez utiliser le code de sécurité suivant lorsqu'il vous sera demandé." + "<br>");
        html.append("Code de sécurité : <strong>" + user.getEmailToken() + "</strong><br>");
        html.append("<br>");
        html.append("Si vous ne vous êtes pas récemment connecté à la plateforme HouseSkipper à partir d'un nouvel appareil, il serait plus sûr de changer le mot de passe de votre compte." + "<br>");
        html.append("<br><br>");
        html.append("Cordialement," + "<br>");
        html.append("<br>");
        html.append("L'assistance HouseSkipper </html>");
        sendHtmlEmail("Connexion Houseskipper", html.toString(), user.getUsername());
    }

    // TODO Ne pas oublier de générer un nouveau emailToken et de le repasser à invalid
    public void sendChangeConfirmationEmail(ApplicationUser user) throws MessagingException {
        String url = env.getProperty("client.url")+"/#/validateAccount/";
        String html = "<html>Bonjour "+user.getFirstname()+"<br>"+"Veuillez confirmer votre nouvelle adresse email en cliquant : <a href='"+url+user.getEmailToken()+"'>Ici</a></html>";

        sendHtmlEmail("Changement d'adresse email", html, user.getUsername());
    }

}
