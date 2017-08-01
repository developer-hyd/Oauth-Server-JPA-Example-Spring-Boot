package com.courseheap.Utils;

/**
 * Created by ashish.p on 1/8/17.
 */
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendMailToUser(String toEmailId, String verifyToken) {
        String message = "Please click on the below link to verify your account \n"
                + "http://localhost:8080/signup/verify?token=" + verifyToken;
        return isValidEmailAddress(toEmailId) && sendMail(toEmailId, message);
    }


    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private boolean sendMail(String toEmail, String message) {
        boolean status;
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Verification mail from CourseHeap.");
            mailMessage.setText(message);
            mailMessage.setFrom("admin@courseHeap.com");
            javaMailSender.send(mailMessage);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }

        return status;
    }
}
