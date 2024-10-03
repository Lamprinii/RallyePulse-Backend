
package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.service.CompetitorService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.SpecialStageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/getspecialstages")
    public ResponseEntity<List<SpecialStage>> getSpecialStages() {
        try {
            List<SpecialStage> specialStages = specialStageService.getAllSpecialStage();
            return new ResponseEntity<>(specialStages, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/ss/{id}")
    public ResponseEntity<SpecialStage> getSpecialStage(@PathVariable("id") Long id) {
        try {
            Optional<SpecialStage> specialstage = specialStageService.getSpecialStageById(id);
            if(specialstage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(specialstage.get(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<SpecialStage> addSpecialStage(@RequestBody SpecialStage specialStage) {
        try {
            SpecialStage newspecialstage = specialStageService.saveSpecialStage(specialStage);
            return new ResponseEntity<>(newspecialstage, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
