package com.inn.cafe.utils;
import com.inn.cafe.POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
}

