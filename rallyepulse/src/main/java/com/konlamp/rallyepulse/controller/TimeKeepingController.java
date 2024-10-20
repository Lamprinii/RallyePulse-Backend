package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.secondary.FinishTime;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.model.secondary.StartTime;
import com.konlamp.rallyepulse.service.CategoryService;
import com.konlamp.rallyepulse.service.EmailService;
import com.konlamp.rallyepulse.service.TimeKeepingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

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
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path="/modifystart")
    public ResponseEntity<TimeKeeping> StartModify(@RequestBody StartTime starttime) {
        try {
            LocalTime time = LocalTime.of(starttime.getHour(), starttime.getMinute(), starttime.getSecond(),starttime.getNano());
            TimeKeeping timekeeping = timeKeepingService.startmodify(starttime.getCo_number(), starttime.getStage(), time, starttime.getDecimal());
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
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
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path="/stop")
    public ResponseEntity<TimeKeeping> Stop(@RequestBody FinishTime finishtime) {
        try {
            LocalTime time = LocalTime.of(finishtime.getHour(), finishtime.getMinute(), finishtime.getSecond(),finishtime.getNano());
            TimeKeeping timekeeping = timeKeepingService.stop(finishtime.getCo_number(), finishtime.getStage(), time, finishtime.getDecimal());
            return new ResponseEntity<>(timekeeping, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getStageClassification/{id}")
    public ResponseEntity<List<TimeKeeping>> stageclassification(@PathVariable("id") Long stage_id) {
        try {
            return new ResponseEntity<>(timeKeepingService.stage_classification(stage_id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
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

    @GetMapping(path = "getOverallClassificationByClass/{id}")
    public ResponseEntity<List<Overall>> overallclassificationbyclass(@PathVariable ("id") String class_id) {
        try {

            return new ResponseEntity<>(timeKeepingService.OverallClassificationByClass(class_id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getOverallClassificationByCategory/{id}")
    public ResponseEntity<List<Overall>> overallclassificationbycategory(@PathVariable ("id") String category_id) {
        try {

            return new ResponseEntity<>(timeKeepingService.OverallClassificationByCategory(category_id), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "getOverallByStage/{id}")
    public ResponseEntity<List<Overall>> overallbystage(@PathVariable("id") Long stage_id) {
        try {
            return new ResponseEntity<>(timeKeepingService.OverallClassificationByStage(stage_id), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}

