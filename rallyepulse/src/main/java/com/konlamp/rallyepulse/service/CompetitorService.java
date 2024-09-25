package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import jakarta.persistence.EntityNotFoundException;
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
}
