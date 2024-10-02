package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.secondary.FinishTime;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.model.secondary.StartTime;
import com.konlamp.rallyepulse.service.CategoryService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.TimeKeepingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/time")
public class TimeKeepingController {
    private final TimeKeepingService timeKeepingService;
    private final EmailService emailService;


    @Autowired
    public TimeKeepingController(TimeKeepingService timeKeepingService, EmailService emailService) {
        this.timeKeepingService = timeKeepingService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<TimeKeeping> Start(@RequestBody StartTime starttime) {
        try {
            LocalTime time = LocalTime.of(starttime.getHour(), starttime.getMinute(), starttime.getSecond(),starttime.getNano());
            TimeKeeping timekeeping = timeKeepingService.start(starttime.getCo_number(), starttime.getStage(), time, starttime.getDecimal());
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<TimeKeeping> Finish(@RequestBody FinishTime finishtime) {
        try {
            LocalTime time = LocalTime.of(finishtime.getHour(), finishtime.getMinute(), finishtime.getSecond(),finishtime.getNano());
            TimeKeeping timekeeping = timeKeepingService.finish(finishtime.getCo_number(), finishtime.getStage(), time, finishtime.getDecimal());
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getStageClassification/{id}")
    public ResponseEntity<List<TimeKeeping>> stageclassification(@PathVariable("id") Long stage_id) {
        try {

            return new ResponseEntity<>(timeKeepingService.stage_classification(stage_id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getOverallClassification")
    public ResponseEntity<List<Overall>> overallclassification() {
        try {

            return new ResponseEntity<>(timeKeepingService.OverallClassification(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getOverallByStage/{id}")
    public ResponseEntity<List<Overall>> overallbystage(@PathVariable("id") Long stage_id) {
        try {
            return new ResponseEntity<>(timeKeepingService.OverallClassificationByStage(stage_id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}

