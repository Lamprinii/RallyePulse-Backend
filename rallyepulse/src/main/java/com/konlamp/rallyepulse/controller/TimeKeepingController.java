package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.service.CategoryService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.TimeKeepingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class TimeKeepingController {
    private final TimeKeepingService timeKeepingService;
    private final EmailService emailService;


    @Autowired
    public TimeKeepingController(TimeKeepingService timeKeepingService, EmailService emailService) {
        this.timeKeepingService = timeKeepingService;
        this.emailService = emailService;
    }



}

