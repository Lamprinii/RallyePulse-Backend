package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.TimeKeepingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeKeepingService {

    private final TimeKeepingRepository timeKeepingRepository;

}
