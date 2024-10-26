package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.model.StartList;
import com.konlamp.rallyepulse.model.secondary.SlistEntry;
import com.konlamp.rallyepulse.model.secondary.SlistEntryM;
import com.konlamp.rallyepulse.model.secondary.SlistEntryMC;
import com.konlamp.rallyepulse.service.SpecialStageService;
import com.konlamp.rallyepulse.service.StartListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/startlist")
@RequiredArgsConstructor
public class StartListController {

    public final StartListService startListService;

    @GetMapping(path = "/getstartlist")
    public ResponseEntity<List<StartList>> getSpecialStages() {
        try {
            List<StartList> startList = startListService.getStartList();
            return new ResponseEntity<>(startList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<List<StartList>> begin(@RequestBody SlistEntryM slistEntry ) {
        try {
            List<StartList>list =startListService.createBeginStartList(LocalTime.of(Math.toIntExact(slistEntry.getMinutes()), Math.toIntExact(slistEntry.getMinutes())));
            startListService.pdf( LocalDate.of(LocalDate.now().getYear(), Math.toIntExact(slistEntry.getMonth()), Math.toIntExact(slistEntry.getDay())),"TC0");
            return new ResponseEntity<>( list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);        }
    }

    @PostMapping(path = "/byoverall")
    public ResponseEntity<List<StartList>> byoverall(@RequestBody SlistEntryM slistEntry) {
        try {
            List<StartList>list =startListService.new_Leg(LocalTime.of(Math.toIntExact(slistEntry.getHours()), Math.toIntExact(slistEntry.getMinutes())));
            startListService.pdf( LocalDate.of(LocalDate.now().getYear(), Math.toIntExact(slistEntry.getMonth()), Math.toIntExact(slistEntry.getDay())),"LEG 2");
            return new ResponseEntity<>( list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);        }
    }

    @PutMapping(path="/changetime")
    public ResponseEntity<List<StartList>> changetime(@RequestBody SlistEntryMC slistEntry) {
        try {
            List<StartList>list =startListService.changeTime(LocalTime.of(Math.toIntExact(slistEntry.getHours()), Math.toIntExact(slistEntry.getMinutes())),slistEntry.getId());
            startListService.pdf( LocalDate.of(LocalDate.now().getYear(), Math.toIntExact(slistEntry.getMonth()), Math.toIntExact(slistEntry.getDay())),"LEG 2");
            return new ResponseEntity<>( list,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path="/changeposistion")
    public ResponseEntity<List<StartList>> changeposistion(@RequestBody SlistEntry slistEntry){
        try {
            List<StartList>list =startListService.changePosistion(slistEntry.getId(), Math.toIntExact(slistEntry.getPosistion()));
            startListService.pdf( LocalDate.of(LocalDate.now().getYear(), Math.toIntExact(slistEntry.getMonth()), Math.toIntExact(slistEntry.getDay())),"LEG 2");
            return new ResponseEntity<>( list,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<StartList> delete(@RequestBody Long id) {
        startListService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
