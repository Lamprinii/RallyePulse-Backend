package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.secondary.FinishTime;
import com.konlamp.rallyepulse.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalty")
public class PenaltyController {
    private final PenaltyService penaltyService;

    @PutMapping(path = "addPenalty")
    public ResponseEntity<Penalty> AddPenalty(@RequestBody Penalty penalty) {
        try {
            penalty=penaltyService.addPenalty(penalty.getTime(),penalty.getCo_number());
            return new ResponseEntity<>(penalty, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "getPenalties")
    public ResponseEntity<List<Penalty>> getCompetitors() {
        try {
            List<Penalty> penalties = penaltyService.getPenalties();
            return new ResponseEntity<>(penalties, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getCompetitor/{id}")
    public ResponseEntity<Penalty> getCompetitor(@PathVariable("id") Long id) {
        try {
            Penalty penalty = penaltyService.getPenaltybyid(id);

            return new ResponseEntity<>(penalty, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }
