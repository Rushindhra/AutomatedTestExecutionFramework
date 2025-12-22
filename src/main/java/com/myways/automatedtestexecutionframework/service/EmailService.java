
package com.myways.automatedtestexecutionframework.service;
import com.myways.automatedtestexecutionframework.entity.TestExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.recipients}")
    private String recipients;

    // AUTO EMAIL – fixed recipients
    public void sendFailureEmail(TestExecution exec) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipients.split(","));
        message.setSubject("❌ Test Failed: " + exec.getTriggeredBy());
        message.setText("Test failed details...");
        mailSender.send(message);
    }

    // MANUAL EMAIL – dynamic recipient
    public void sendSimpleMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to); // ✅ dynamic
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}


