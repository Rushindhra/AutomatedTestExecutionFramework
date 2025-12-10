package com.myways.automatedtestexecutionframework.service;

import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.recipients}")
    private String defaultRecipients;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Autowired
    private org.springframework.core.env.Environment env;
    @PostConstruct
    public void debugConfig() {
        System.out.println("ENV USERNAME: " + env.getProperty("spring.mail.username"));
        System.out.println("ENV PASSWORD: " + env.getProperty("spring.mail.password"));
    }

    private String[] parseRecipients(String recipients) {
        return Arrays.stream(recipients.split(","))
                .map(String::trim)
                .toArray(String[]::new);
    }

    private boolean isValidEmail(String email) {
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendSimpleMail(String[] to, String subject, String messageText) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(extractEmail(from));
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(messageText);

        mailSender.send(msg);
    }

    public void sendMailWithAttachment(String[] to, String subject, String text, String attachmentPath)
            throws MessagingException {

        MimeMessage mime = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mime, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        if (attachmentPath != null) {
            File file = new File(attachmentPath);
            if (file.exists()) {
                helper.addAttachment(file.getName(), new FileSystemResource(file));
            }
        }

        mailSender.send(mime);
    }

    private String extractEmail(String fromValue) {
        if (fromValue.contains("<") && fromValue.contains(">")) {
            return fromValue.substring(fromValue.indexOf("<") + 1, fromValue.indexOf(">"));
        }
        return fromValue;
    }

    public String[] getDefaultRecipients() {
        return parseRecipients(defaultRecipients);
    }

    public boolean validateEmailOrNull(String email) {
        return email == null || email.isBlank() || isValidEmail(email);
    }


    @PostConstruct
    public void verifyMailConfig() {
        if (mailSender instanceof org.springframework.mail.javamail.JavaMailSenderImpl impl) {
            System.out.println("MAIL SENDER USERNAME = " + impl.getUsername());
            System.out.println("MAIL SENDER PASSWORD = " + impl.getPassword());
        } else {
            System.out.println("MailSender is NOT JavaMailSenderImpl!");
        }
    }


}
