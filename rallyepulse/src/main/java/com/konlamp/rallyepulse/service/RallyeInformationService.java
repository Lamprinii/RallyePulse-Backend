package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.RallyeInformation;
import com.konlamp.rallyepulse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RallyeInformationService {

    private final PenaltyRepository penaltyRepository;
    private final CompetitorRepository competitorRepository;
    private final TimeKeepingRepository timeKeepingRepository;
    private final SpecialStageRepository specialStageRepository;
    private final StartListRepository startListRepository;
    private final CarClassRepository carClassRepository;
    private final CategoryRepository categoryRepository;


    private final RallyeInformationRepository rallyeInformationRepository;
    public RallyeInformation addrallye(RallyeInformation rallyeInformation) {
        return rallyeInformationRepository.save(rallyeInformation);
    }
    public void setfinal() {
        getRallyeInformation().setResults(true);
    }
    public RallyeInformation getRallyeInformation() {
        return rallyeInformationRepository.findById(1L).orElse(null);
    }
    public boolean deleterace() {
        penaltyRepository.deleteAll();
        competitorRepository.deleteAll();
        timeKeepingRepository.deleteAll();
        specialStageRepository.deleteAll();
        startListRepository.deleteAll();
        carClassRepository.deleteAll();
        categoryRepository.deleteAll();
        rallyeInformationRepository.deleteAll();
        return true;
    }

}
