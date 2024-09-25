 package com.konlamp.rallyepulse.service;

 import jakarta.mail.MessagingException;
 import jakarta.mail.internet.MimeMessage;
 import lombok.RequiredArgsConstructor;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.io.FileSystemResource;
 import org.springframework.mail.javamail.JavaMailSender;
 import org.springframework.mail.javamail.MimeMessageHelper;
 import org.springframework.stereotype.Service;

 import java.io.File;
 import java.time.LocalDateTime;

 @RequiredArgsConstructor
@Service
public class EmailService {

     @Autowired
     private final JavaMailSender javaMailSender;




}

