package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.PenaltyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PenaltyService {

    private final PenaltyRepository penaltyRepository;

    public List<Penalty> getPenalties() {
        return penaltyRepository.findAll();
    }

    public Penalty addNewPenalty(Penalty penalty) {
        return penaltyRepository.save(penalty);
    }

    public Penalty getPenaltybyid(Long id) {
        return penaltyRepository.findById(id).get();
    }

public Penalty addPenalty(LocalTime time, Long co_number) {
        Penalty penalty = getPenaltybyid(co_number);
        penalty.setTime(penalty.getTime().plusNanos(time.toNanoOfDay()));
        return addNewPenalty(penalty);
}

}
