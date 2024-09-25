package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.repository.CategoryRepository;
import com.konlamp.rallyepulse.repository.SpecialStageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialStageService {

    private final SpecialStageRepository specialStageRepository;
    public List<SpecialStage> getAllSpecialStage() {
        return specialStageRepository.findAll();
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


