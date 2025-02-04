package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.secondary.AddPenalty;
import com.konlamp.rallyepulse.model.secondary.FinishTime;
import com.konlamp.rallyepulse.service.PenaltyService;
import com.konlamp.rallyepulse.service.RallyeInformationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalty")
public class PenaltyController {
    private final PenaltyService penaltyService;
    private final RallyeInformationService rallyeInformationService;

    @PutMapping(path = "addPenalty")
    public ResponseEntity<Penalty> AddPenalty(@RequestBody AddPenalty penalty) {
        try {
            if (rallyeInformationService.getRallyeInformation().isResults()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Penalty penaltyinsert =penaltyService.addPenalty(LocalTime.of(0, penalty.getMinute(), penalty.getSecond()),penalty.getCo_number());
            return new ResponseEntity<>(penaltyinsert, HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getPenalties")
    public ResponseEntity<List<Penalty>> getPenalties() {
        try {
            List<Penalty> penalties = penaltyService.getPenalties();
            return new ResponseEntity<>(penalties, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getPenalty/{id}")
    public ResponseEntity<Penalty> getPenalty(@PathVariable("id") Long id) {
        try {
            Penalty penalty = penaltyService.getPenaltybyid(id);
            return new ResponseEntity<>(penalty, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    }
