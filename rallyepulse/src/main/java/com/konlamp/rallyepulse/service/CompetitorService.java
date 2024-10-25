package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitorService {

    private final CompetitorRepository competitorRepository;

    public List<Competitor> getCompetitors(){
        return competitorRepository.findAll();
    }

    public Optional<Competitor> getCompetitorbyid(Long id) {
        return competitorRepository.findById(id);
    }

    public Competitor addNewCompetitor(Competitor competitor) {
        boolean exists = competitorRepository.existsById(competitor.getCo_number());
        if(exists) {
            throw new IllegalStateException("A competitor with this number already exists");
        }
        return competitorRepository.save(competitor);
    }
    @Transactional
    public Competitor updateCompetitor(Competitor competitor) {
        boolean exists = competitorRepository.existsById(competitor.getCo_number());
        if(!exists) {
            throw new IllegalStateException("A competitor with this number does not exists");
        }
       Competitor old_competitor = competitorRepository.findById(competitor.getCo_number()).orElseThrow();
        if (!competitor.getDriver().equals(old_competitor.getDriver())) {
          old_competitor.setDriver(competitor.getDriver());
        } if(!competitor.getCodriver().equals(old_competitor.getCodriver())) {
          old_competitor.setCodriver(competitor.getCodriver());
        } if(!competitor.getEmail().equals(old_competitor.getEmail())) {
            old_competitor.setEmail(competitor.getEmail());
        } if(!competitor.getTelephone().equals(old_competitor.getTelephone())) {
            old_competitor.setTelephone(competitor.getTelephone());
        } if(!competitor.getVehicle().equals(old_competitor.getVehicle())) {
            old_competitor.setVehicle(competitor.getVehicle());
        } if (!competitor.getCar_class().equals(old_competitor.getCar_class())) {
            old_competitor.setCar_class(competitor.getCar_class());
        } if (!competitor.getCategory().equals(old_competitor.getCategory())) {
            old_competitor.setCategory(competitor.getCategory());
        }
        return old_competitor;
    }

    public Competitor findByPassCode(String passCode) {
        List<Competitor> passes=competitorRepository.findCompetitorByPasscode(passCode);
        if(passes.isEmpty()){
            throw new EntityNotFoundException("Competitor with this passcode does not exist");
        }
        return passes.get(0);
    }
}
