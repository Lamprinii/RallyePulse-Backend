package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.repository.CategoryRepository;
import com.konlamp.rallyepulse.repository.SpecialStageRepository;
import com.konlamp.rallyepulse.repository.TimeKeepingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialStageService {

    private final SpecialStageRepository specialStageRepository;
    private final TimeKeepingRepository timeKeepingRepository;
    public List<SpecialStage> getAllSpecialStage() {
        return specialStageRepository.findAll();
    }

    public List<SpecialStage> getAllStartedSpecialStage() {
        List<SpecialStage> stages = specialStageRepository.findAll();
        List<TimeKeeping> times = timeKeepingRepository.findAll();
        List<SpecialStage> specialStages = new ArrayList<>();
        for (SpecialStage stage : stages) {
            for (TimeKeeping timeKeeping : times) {
                if (stage.getId() == timeKeeping.getId().getSpecialstageid()) {
                    if (timeKeeping.getFinish_time() != null) {
                        specialStages.add(stage);
                        break;
                    }
                }
            }
        }
        return specialStages;
    }
    public  Optional<SpecialStage> getSpecialStageById(Long id) {
        return specialStageRepository.findById(id);
    }
    public SpecialStage saveSpecialStage(SpecialStage specialStage) {
        boolean exists = specialStageRepository.existsById(specialStage.getId());
        if(exists) {
            throw new IllegalStateException("A special stage with this number already exists");
        }
        return specialStageRepository.save(specialStage);
    }
}


