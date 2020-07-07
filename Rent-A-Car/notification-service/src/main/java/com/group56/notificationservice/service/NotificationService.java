package com.group56.notificationservice.service;

import com.group56.notificationservice.dto.EmailDTO;
import com.group56.notificationservice.util.CustomEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private CustomEmailSender customEmailSender;

    @Autowired
    public NotificationService(CustomEmailSender customEmailSender) {
        this.customEmailSender = customEmailSender;
    }

    public ResponseEntity<?> sendEmail(EmailDTO emailDTO) {
        String receiverUsername = emailDTO.getReceiverUsername();

        String rawText = emailDTO.getContent();
        String formattedText = "<html><body><h3>" + rawText + "</h3></body></html>";

        return customEmailSender.sendMail(new String[]{receiverUsername}, "Rent A Car", formattedText);
    }
}
