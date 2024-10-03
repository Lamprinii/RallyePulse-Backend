package com.konlamp.rallyepulse.controller;


import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.service.CompetitorService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.PenaltyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/competitor")
public class CompetitorController {
    private final CompetitorService competitorService;
    private final EmailService emailService;
    private final PenaltyService penaltyService;


    @Autowired
    public CompetitorController(CompetitorService competitorService,EmailService emailService,PenaltyService penaltyService) {
        this.competitorService = competitorService;
        this.emailService = emailService;
        this.penaltyService = penaltyService;
    }

    @GetMapping(path = "getCompetitors")
    public ResponseEntity<List<Competitor>> getCompetitors() {
        try {
            List<Competitor> competitors = competitorService.getCompetitors();
            return new ResponseEntity<>(competitors, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "getCompetitor/{id}")
    public ResponseEntity<Competitor> getCompetitor(@PathVariable("id") Long id) {
        try {
            Optional<Competitor> competitor = competitorService.getCompetitorbyid(id);
            if(competitor.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(competitor.get(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<Competitor> addCompetitor(@RequestBody Competitor competitor) {
        try {
            Competitor newcompetitor = competitorService.addNewCompetitor(competitor);
            penaltyService.addNewPenalty(new Penalty(competitor.getCo_number(), LocalTime.of(0,0,0,0)));
            return new ResponseEntity<>(newcompetitor, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
