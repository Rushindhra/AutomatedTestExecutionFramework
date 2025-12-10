package com.myways.automatedtestexecutionframework.controller;
import com.myways.automatedtestexecutionframework.dto.EmailRequest;
import com.myways.automatedtestexecutionframework.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;
import java.util.HashMap;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody EmailRequest request) {

        try {
            String subject = request.getSubject() != null ? request.getSubject() : "Automated Notification";

            // Validate recipient
            if (!emailService.validateEmailOrNull(request.getTo())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid email format"));
            }

            String[] recipients;

            if (request.getTo() == null || request.getTo().isBlank()) {
                recipients = emailService.getDefaultRecipients();
            } else {
                recipients = new String[]{request.getTo().trim()};
            }

            if (request.getAttachmentPath() != null) {
                // Send with attachment
                emailService.sendMailWithAttachment(recipients, subject, request.getMessage(), request.getAttachmentPath());
            } else {
                // Send simple mail
                emailService.sendSimpleMail(recipients, subject, request.getMessage());
            }

            Map<String, Object> resp = new HashMap<>();
            resp.put("status", "SUCCESS");
            resp.put("sentTo", recipients);
            resp.put("timestamp", Instant.now().toString());
            return ResponseEntity.ok(resp);

        } catch (MessagingException ex) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Failed to send email with attachment",
                    "details", ex.getMessage()
            ));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Email send failed",
                    "details", ex.getMessage()
            ));
        }
    }
}
