package com.example.topichubbackend.services.impls;

import com.example.topichubbackend.entity.*;
import com.example.topichubbackend.services.interfaces.*;
import jakarta.mail.*;
import jakarta.mail.Session;
import jakarta.mail.internet.*;

import java.util.*;

public class EmailService implements IEmailService {
    private final static EmailService emailService = new EmailService();
    private final Session session;

    private final String email;
    private final String password;

    private EmailService() { }
    public static EmailService  getInstance(){
        return emailService;
    }
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");
        email = System.getenv("email")!=null ? System.getenv("email") : "e.shostak05@gmail.com";
        password =  System.getenv("password")!=null ? System.getenv("password") : "dddy raua wzso zgbs";
        session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
    }

    @Override
    public void sendCommentNotification(Comment comment, User author) {

        try {
            // create a MimeMessage object
            Message message = new MimeMessage(session);
            // set From email field
            message.setFrom(new InternetAddress(email));
            // set To email field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(author.getEmail()));
            // set email subject field
            message.setSubject("Comment notification");
            // set the content of the email message
            message.setText("You have a new comment to article: "+comment.getArticle().getTheme());
            // send the email message
            Transport.send(message);

        } catch (MessagingException e) {
        }
    }
}
