
package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.service.CompetitorService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.SpecialStageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/specialstage")
public class SpecialStageController {
    private final SpecialStageService specialStageService;
    private final EmailService emailService;


    @Autowired
    public SpecialStageController(SpecialStageService specialStageService,EmailService emailService) {
        this.specialStageService = specialStageService;
        this.emailService = emailService;
    }



}
