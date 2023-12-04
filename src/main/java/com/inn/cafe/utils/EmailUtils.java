package com.inn.cafe.utils;
import com.inn.cafe.POJO.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;


    public void sendSimpleMesage(String to, String subject, String text, List<String> allAdmins) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nutribros.email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if(allAdmins != null && allAdmins.size() > 0) {
            message.setCc(getCcArray(allAdmins));
        }
        emailSender.send(message);
    }

    private String[] getCcArray(List<String> allAdmins) {
        String[] ccArray = new String[allAdmins.size()];
        for (int i = 0; i < allAdmins.size(); i++) {
            ccArray[i] = allAdmins.get(i);
        }
        return ccArray;
    }

    public void forgotMail(String to, String subject, String password) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("nutribros.email@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to +
                " <br><b>Password: </b> " + password +
                "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
        message.setContent(htmlMsg, "text/html");
        emailSender.send(message);
    }
}

