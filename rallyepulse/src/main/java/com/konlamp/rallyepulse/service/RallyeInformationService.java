package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.RallyeInformation;
import com.konlamp.rallyepulse.repository.PenaltyRepository;
import com.konlamp.rallyepulse.repository.RallyeInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RallyeInformationService {

    private final RallyeInformationRepository rallyeInformationRepository;
    public RallyeInformation addrallye(RallyeInformation rallyeInformation) {
        return rallyeInformationRepository.save(rallyeInformation);
    }
    public RallyeInformation getRallyeInformation() {
        return rallyeInformationRepository.findById(1L).orElse(null);
    }

}
