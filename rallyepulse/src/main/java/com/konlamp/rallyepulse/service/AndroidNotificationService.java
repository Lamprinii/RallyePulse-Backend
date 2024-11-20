package com.konlamp.rallyepulse.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class AndroidNotificationService {

    public String sendNotificationToTopic(String title, String body) {
        try {
            // Δημιουργία του Notification
            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            // Δημιουργία μηνύματος προς το topic "Alerts"
            Message message = Message.builder()
                    .setTopic("Alerts") // Το όνομα του topic
                    .setNotification(notification)
                    .build();

            // Αποστολή του μηνύματος στο Firebase
            return FirebaseMessaging.getInstance().send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send notification to topic", e);
        }
    }
}
