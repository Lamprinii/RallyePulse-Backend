package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.service.CarClassService;
import com.konlamp.rallyepulse.service.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carclass")
public class CarClassController {
    private final CarClassService carClassService;
    private final EmailService emailService;


    @Autowired
    public CarClassController(CarClassService carClassService,EmailService emailService) {
        this.carClassService = carClassService;
        this.emailService = emailService;
    }



}
