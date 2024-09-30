package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.Penalty;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.TimeKeepingid;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.repository.CompetitorRepository;
import com.konlamp.rallyepulse.repository.TimeKeepingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeKeepingService {

    private final TimeKeepingRepository timeKeepingRepository;
    private final CompetitorService competitorService;
    private final PenaltyService penaltyService;



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

    public List<TimeKeeping> stage_classification(Long stage_id){
        List <TimeKeeping> stagetimes = timeKeepingRepository.findByIdSpecialstageid(stage_id);
        int i=0;
        int j=0;
        int k=0;
        while (j<stagetimes.size()) {
            i=j;
            k = j;
            TimeKeeping min = stagetimes.get(j);
            while (i < stagetimes.size()) {
                if (min.getTotal_time().compareTo(stagetimes.get(i).getTotal_time()) >0) {
                    min = stagetimes.get(i);
                    k = i;
                }
                i++;
            }
            TimeKeeping temp = stagetimes.get(j);
            stagetimes.set(k, temp);
            stagetimes.set(j, min);
            j++;
        }

        return stagetimes;
    }

    public List<Overall> OverallClassification() {
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        for (Competitor competitor : competitors) {
            numbers.add(competitor.getCo_number());
        }
        List <Overall> overall = new ArrayList<>();
        for (Long number : numbers) {
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            LocalTime total = LocalTime.of(0, 0, 0, 0);
            for (TimeKeeping timekeeping : temp) {
                System.out.println(total.toString());
                System.out.println(timekeeping.getTotal_time().toString());
                total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
            }
            Penalty penalty = penaltyService.getPenaltybyid(number);
            total = total.plusNanos(penalty.getTime().toNanoOfDay());
            overall.add(new Overall(number, total));
        }
        int i=0;
        int j=0;
        int k=0;
        while (j<overall.size()) {
            i=j;
            k = j;
            Overall min = overall.get(j);
            while (i < overall.size()) {
                if (min.getTime().compareTo(overall.get(i).getTime()) >0) {
                    min = overall.get(i);
                    k = i;
                }
                i++;
            }
            Overall temp = overall.get(j);
            overall.set(k, temp);
            overall.set(j, min);
            j++;
        }



        return overall;
    }
}
