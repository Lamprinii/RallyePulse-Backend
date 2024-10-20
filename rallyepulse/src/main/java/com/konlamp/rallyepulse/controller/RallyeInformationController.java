package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.model.RallyeInformation;
import com.konlamp.rallyepulse.service.RallyeInformationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/penalty")
public class RallyeInformationController {


    private final RallyeInformationService rallyeInformationService;

    @GetMapping(path = "getRallye")
    public ResponseEntity<RallyeInformation> getRallyeInformation() {
        try {
            RallyeInformation rallyeInformation = rallyeInformationService.getRallyeInformation();
            return new ResponseEntity<>(rallyeInformation, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping
    public ResponseEntity<RallyeInformation> addRallyeInformation(@RequestBody RallyeInformation rallyeInformation) {
        try{

            RallyeInformation rallye = rallyeInformationService.addrallye(rallyeInformation);
            return new ResponseEntity<>(rallye, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

