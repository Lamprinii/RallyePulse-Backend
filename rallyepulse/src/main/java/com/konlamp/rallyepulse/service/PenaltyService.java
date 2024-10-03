package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.PenaltyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;
    private final CompetitorService competitorService;

    public List<Penalty> getPenalties() {
        return penaltyRepository.findAll();
    }

    public Penalty addNewPenalty(Penalty penalty) {
        Optional<Competitor> comp = competitorService.getCompetitorbyid(penalty.getCo_number());
        if (comp.isPresent()) {
            return penaltyRepository.save(penalty);
        } else {
            throw new EntityNotFoundException("Competitor not found");
        }
    }

    public Penalty getPenaltybyid(Long id) {
        return penaltyRepository.findById(id).get();
    }

public Penalty addPenalty(LocalTime time, Long co_number) {
        Optional <Competitor> comp = competitorService.getCompetitorbyid(co_number);
        if (comp.isPresent()) {
            Penalty penalty = getPenaltybyid(co_number);
            penalty.setTime(penalty.getTime().plusNanos(time.toNanoOfDay()));
            return addNewPenalty(penalty);
        } else {
            throw new EntityNotFoundException("Competitor not found");
        }


}

}
