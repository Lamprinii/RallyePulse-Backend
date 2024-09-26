package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.TimeKeepingid;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.TimeKeepingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeKeepingService {

    private final TimeKeepingRepository timeKeepingRepository;

    public LocalTime CalculateStageTime (LocalTime start_time, LocalTime finish_time, int decimal) {
        int nano_start = start_time.getNano();
        int nano_finish = finish_time.getNano();

        while (nano_start > decimal) {
            nano_start = nano_start / 10;
        }

        while (nano_finish > decimal) {
            nano_finish = nano_finish / 10;
        }

        int seconds = 0;
        int minutes = 0;
        int hours = 0;

        int nano = nano_finish - nano_start;
        if (nano < 0) {
            nano = nano + decimal;
            seconds= seconds -1;
        }
        seconds = finish_time.getSecond() - start_time.getSecond() + seconds;
        if (seconds < 0) {
            seconds = seconds + 60;
            minutes = minutes - 1;
        }

        minutes = finish_time.getMinute() - start_time.getMinute() + minutes;
        if (minutes < 0) {
            minutes = minutes + 60;
            hours = hours - 1;
        }

        hours = finish_time.getHour() - start_time.getHour() + hours;
        if (nano != 0) {
            while (nano < 100000000) {
                nano = nano * 10;
            }
        }
        LocalTime stagetime = LocalTime.of(hours, minutes, seconds, nano);

        return stagetime;

    }
    public TimeKeeping finish(Long co_number, Long stage, LocalTime finish_time, int decimal){
        Optional <TimeKeeping> timekeeping = timeKeepingRepository.findById(new TimeKeepingid(co_number, stage));
        if (timekeeping.isPresent()) {
            TimeKeeping time = timekeeping.get();
            LocalTime stage_time = CalculateStageTime(time.getStart_time(), finish_time, decimal);
            time.setFinish_time(finish_time);
            time.setTotal_time(stage_time);
            return timeKeepingRepository.save(time);
        }
        else {
            throw new EntityNotFoundException("Timekeeping not found");
        }
    }

    public TimeKeeping start(Long co_number, Long stage, LocalTime start_time, int decimal){
        Optional <TimeKeeping> timekeeping = timeKeepingRepository.findById(new TimeKeepingid(co_number, stage));
        if (timekeeping.isPresent()) {
            throw new IllegalStateException("This car has already started this stage");
        }
        TimeKeeping time = new TimeKeeping(new TimeKeepingid(co_number, stage), start_time);

        return timeKeepingRepository.save(time);
    }
}
