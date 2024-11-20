package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.service.AndroidNotificationService;
import com.konlamp.rallyepulse.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class AndroidNotificationController {

    @Autowired
    private AndroidNotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestBody String title
             ) {
            String body = "test";
            System.out.println(title+"HIIIIIIIIIIIIIIIIIIII I I ");
        try {
            String response = notificationService.sendNotificationToTopic(title, body);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
        }
    }
}
