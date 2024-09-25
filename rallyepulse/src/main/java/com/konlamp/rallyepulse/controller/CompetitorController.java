package com.konlamp.rallyepulse.controller;


import com.konlamp.rallyepulse.service.CompetitorService;
import com.konlamp.rallyepulse.service.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/competitor")
public class CompetitorController {
    private final CompetitorService competitorService;
    private final EmailService emailService;


    @Autowired
    public CompetitorController(CompetitorService competitorService,EmailService emailService) {
        this.competitorService = competitorService;
        this.emailService = emailService;
    }



}
