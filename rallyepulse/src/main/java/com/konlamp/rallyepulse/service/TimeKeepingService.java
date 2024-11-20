package com.konlamp.rallyepulse.service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.konlamp.rallyepulse.model.*;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.model.secondary.OverallAndroid;
import com.konlamp.rallyepulse.model.secondary.TimeKeepingAndroid;
import com.konlamp.rallyepulse.model.secondary.TimekeepingExport;
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
    private final SpecialStageService specialStageService;
    private final NotificationService socket;

    public LocalTime CalculateStageTime (LocalTime start_time, LocalTime finish_time, int decimal) {
        int nano_start = start_time.getNano();
        int nano_finish = finish_time.getNano();
        if (decimal == 1 ) {
            decimal = 10;
        } else if (decimal == 2) {
            decimal = 100;
        } else {
            decimal = 1000;
        }
        if(nano_finish<decimal){
            nano_finish = 0;
        }
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
            socket.SendNotification(time);
            return timeKeepingRepository.save(time);
        }
        else {
            throw new EntityNotFoundException("The Car did not Start This Stage");
        }
    }

    public TimeKeeping stop(Long co_number, Long stage, LocalTime finish_time, int decimal){
        Optional <TimeKeeping> timekeeping = timeKeepingRepository.findById(new TimeKeepingid(co_number, stage));
        if (timekeeping.isPresent()) {
            TimeKeeping time = timekeeping.get();
            time.setTotal_time(finish_time);
            socket.SendNotification(time);
            return timeKeepingRepository.save(time);
        }
        else {
            throw new EntityNotFoundException("The Car did not Start This Stage");
        }
    }

    public TimeKeeping startmodify(Long co_number, Long stage, LocalTime start_time, int decimal){
        Optional <TimeKeeping> timekeeping = timeKeepingRepository.findById(new TimeKeepingid(co_number, stage));
        if (timekeeping.isPresent()) {
            TimeKeeping time = timekeeping.get();
            time.setStart_time(start_time);
            if (time.getFinish_time() != null) {
                LocalTime stage_time = CalculateStageTime(time.getStart_time(), time.getFinish_time(), decimal);
                time.setTotal_time(stage_time);
            }
            return timeKeepingRepository.save(time);
        }
        else {
            throw new EntityNotFoundException("The Car did not Start This Stage");
        }
    }

    public TimeKeeping start(Long co_number, Long stage, LocalTime start_time, int decimal){
        if (competitorService.getCompetitorbyid(co_number).isEmpty()) {
            throw new EntityNotFoundException("The competitor does not exist");
        }
        if (specialStageService.getSpecialStageById(stage).isEmpty()) {
            throw new EntityNotFoundException("The special stage does not exist");
        }
        Optional <TimeKeeping> timekeeping = timeKeepingRepository.findById(new TimeKeepingid(co_number, stage));
        if (timekeeping.isPresent()) {
                TimeKeeping time = timekeeping.get();
                time.setStart_time(start_time);
//                if (time.getFinish_time() != null) {
//                    LocalTime stage_time = CalculateStageTime(time.getStart_time(), time.getFinish_time(), decimal);
//                    time.setTotal_time(stage_time);
//                }
                return timeKeepingRepository.save(time);
            }
        TimeKeeping time = new TimeKeeping(new TimeKeepingid(co_number, stage), start_time);

        return timeKeepingRepository.save(time);
    }

    public List<TimekeepingExport> stage_classification(Long stage_id){
        if (specialStageService.getSpecialStageById(stage_id).isEmpty()) {
            throw new EntityNotFoundException("The special stage does not exist");
        }
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
        ArrayList<TimekeepingExport> exports = new ArrayList<>();
        LocalTime first=stagetimes.get(0).getTotal_time();
        LocalTime prev=stagetimes.get(0).getTotal_time();

        for(int l=0;l<stagetimes.size();l++) {
            String difftoPrevS;
            String difftoFirstS;
            if(i!=0) {
                TimeKeeping temp=stagetimes.get(l);
                LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                difftoPrev=temp.getTotal_time().minusNanos(prev.toNanoOfDay());
                difftoFirst=temp.getTotal_time().minusNanos(first.toNanoOfDay());
                if(difftoPrev.getHour()==0){
                    int nano=difftoPrev.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoPrev.getMinute()==0){
                        difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoPrevS=difftoPrev.toString();
                }
                if(difftoFirst.getHour()==0){
                    int nano=difftoFirst.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoFirst.getMinute()==0){
                        difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoFirstS=difftoFirst.toString();
                }
                prev=temp.getTotal_time();
            }
            else{
                 difftoPrevS="+00:00";
                 difftoFirstS="+00:00";
            }
            Competitor comp=competitorService.getCompetitorbyid(stagetimes.get(l).getId().getCompetitorid()).orElseThrow();
            exports.add(new TimekeepingExport(stagetimes.get(l).getId(),stagetimes.get(l).getStart_time(),stagetimes.get(l).getFinish_time(),stagetimes.get(l).getTotal_time(),comp.getDriver()+"-"+comp.getCodriver(),comp.getCar_class(), comp.getCategory(),"+"+difftoFirstS,"+"+difftoPrevS));
        }
//        PdfGenerator pdfGenerator = new PdfGenerator();
//        pdfGenerator.generatestage(stagetimes, competitorService,specialStageService.getSpecialStageById(stage_id).get());
        return exports;
    }

    public ArrayList<TimeKeepingAndroid> stage_classification_android(Long stage_id){
        if (specialStageService.getSpecialStageById(stage_id).isEmpty()) {
            throw new EntityNotFoundException("The special stage does not exist");
        }
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
        ArrayList<TimeKeepingAndroid> stagetimestwo = new ArrayList<>();
        for (int n=0; n < stagetimes.size(); n++) {
            TimeKeepingAndroid temp = new TimeKeepingAndroid(stagetimes.get(n).getId(), stagetimes.get(n).getStart_time().toString(), stagetimes.get(n).getFinish_time().toString(), stagetimes.get(n).getTotal_time().toString(), competitorService.getCompetitorbyid(stagetimes.get(n).getId().getCompetitorid()).orElseThrow().getDriver());
            stagetimestwo.add(temp);
        }
//        PdfGenerator pdfGenerator = new PdfGenerator();
//        pdfGenerator.generatestage(stagetimes, competitorService,specialStageService.getSpecialStageById(stage_id).get());
        return stagetimestwo;
    }


    public List<Overall> OverallClassification() {
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> category = new ArrayList<>();
        List<String> car_class = new ArrayList<>();
        for (Competitor competitor : competitors) {
            numbers.add(competitor.getCo_number());
            names.add(competitor.getDriver()+"-"+competitor.getCodriver());
            category.add(competitor.getCategory());
            car_class.add(competitor.getCar_class());
        }
        List <Overall> overall = new ArrayList<>();
        int n=0;
        for (Long number : numbers) {
           boolean retired = false;
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            LocalTime total = LocalTime.of(0, 0, 0, 0);
            for (TimeKeeping timekeeping : temp) {
                if (timekeeping.getTotal_time() == null) {
                    retired = true;
                    break;
                }
                total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
            }
            if (retired == true) {
                continue;
            }
            Penalty penalty = penaltyService.getPenaltybyid(number);
            total = total.plusNanos(penalty.getTime().toNanoOfDay());
            overall.add(new Overall(number,names.get(n), total,category.get(n),car_class.get(n)));
            overall.get(n).setPenalty(penalty.getTime());
            n++;
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
        LocalTime first=overall.get(0).getTime();
        LocalTime prev=overall.get(0).getTime();
        for(int y=0;y<overall.size();y++){
            String difftoPrevS;
            String difftoFirstS;
            if(y!=0) {
                Overall temp=overall.get(y);
                LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                difftoPrev=temp.getTime().minusNanos(prev.toNanoOfDay());
                difftoFirst=temp.getTime().minusNanos(first.toNanoOfDay());
                if(difftoPrev.getHour()==0){
                    int nano=difftoPrev.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoPrev.getMinute()==0){
                        difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoPrevS=difftoPrev.toString();
                }
                if(difftoFirst.getHour()==0){
                    int nano=difftoFirst.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoFirst.getMinute()==0){
                        difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoFirstS=difftoFirst.toString();
                }
                prev=temp.getTime();
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
            else{
                difftoPrevS="+00:00";
                difftoFirstS="+00:00";
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
        }
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generate(overall, competitorService);
        return overall;
    }

    public List<OverallAndroid> OverallClassification_android() {
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> category = new ArrayList<>();
        List<String> car_class = new ArrayList<>();
        for (Competitor competitor : competitors) {
            numbers.add(competitor.getCo_number());
            names.add(competitor.getDriver()+"-"+competitor.getCodriver());
            category.add(competitor.getCategory());
            car_class.add(competitor.getCar_class());
        }
        List <Overall> overall = new ArrayList<>();
        int l=0;
        for (Long number : numbers) {
            boolean retired = false;
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            LocalTime total = LocalTime.of(0, 0, 0, 0);
            for (TimeKeeping timekeeping : temp) {
                if (timekeeping.getTotal_time() == null) {
                    retired = true;
                    break;
                }
                total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
            }
            if (retired == true) {
                continue;
            }
            Penalty penalty = penaltyService.getPenaltybyid(number);
            total = total.plusNanos(penalty.getTime().toNanoOfDay());
            overall.add(new Overall(number,names.get(l), total,category.get(l),car_class.get(l)));
            l++;
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
        ArrayList<OverallAndroid> overalltwo = new ArrayList<>();


        for (int n=0; n < overall.size(); n++) {
            System.out.println(competitorService.getCompetitorbyid(overall.get(n).getCo_number()).orElseThrow().getDriver());
            OverallAndroid temp = new OverallAndroid(overall.get(n).getCo_number(), overall.get(n).getTime(), competitorService.getCompetitorbyid(overall.get(n).getCo_number()).orElseThrow().getDriver());
            overalltwo.add(temp);
        }
        return overalltwo;
    }

    public List<Overall> OverallClassificationByClass(String car_class) {
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> category = new ArrayList<>();
        List<String> car_classl = new ArrayList<>();
        for (Competitor competitor : competitors) {
            if (competitor.getCar_class().equals(car_class)) {
                numbers.add(competitor.getCo_number());
                names.add(competitor.getDriver()+"-"+competitor.getCodriver());
                category.add(competitor.getCategory());
                car_classl.add(competitor.getCar_class());
            }
        }
        List <Overall> overall = new ArrayList<>();
        int n=0;
        for (Long number : numbers) {
            boolean retired = false;
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            LocalTime total = LocalTime.of(0, 0, 0, 0);
            for (TimeKeeping timekeeping : temp) {
                if (timekeeping.getTotal_time() == null) {
                    retired = true;
                    break;
                }
                total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
            }
            if (retired == true) {
                continue;
            }
            Penalty penalty = penaltyService.getPenaltybyid(number);
            total = total.plusNanos(penalty.getTime().toNanoOfDay());
            overall.add(new Overall(number,names.get(n), total,category.get(n),car_classl.get(n)));
            overall.get(n).setPenalty(penalty.getTime());
            n++;
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
        LocalTime first=overall.get(0).getTime();
        LocalTime prev=overall.get(0).getTime();
        for(int y=0;y<overall.size();y++){
            String difftoPrevS;
            String difftoFirstS;
            if(y!=0) {
                Overall temp=overall.get(y);
                LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                difftoPrev=temp.getTime().minusNanos(prev.toNanoOfDay());
                difftoFirst=temp.getTime().minusNanos(first.toNanoOfDay());
                if(difftoPrev.getHour()==0){
                    int nano=difftoPrev.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoPrev.getMinute()==0){
                        difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoPrevS=difftoPrev.toString();
                }
                if(difftoFirst.getHour()==0){
                    int nano=difftoFirst.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoFirst.getMinute()==0){
                        difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoFirstS=difftoFirst.toString();
                }
                prev=temp.getTime();
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
            else{
                difftoPrevS="+00:00";
                difftoFirstS="+00:00";
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
        }
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generatebycategoryclass(overall, competitorService,"Class", car_class);
        return overall;
    }

    public List<Overall> OverallClassificationByCategory(String category) {
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> categoryl = new ArrayList<>();
        List<String> car_class = new ArrayList<>();
        for (Competitor competitor : competitors) {
            if (competitor.getCategory().equals(category)) {
                numbers.add(competitor.getCo_number());
                names.add(competitor.getDriver()+"-"+competitor.getCodriver());
                categoryl.add(competitor.getCategory());
                car_class.add(competitor.getCar_class());
            }
        }
        List <Overall> overall = new ArrayList<>();
        int n=0;
        for (Long number : numbers) {
            boolean retired = false;
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            LocalTime total = LocalTime.of(0, 0, 0, 0);
            for (TimeKeeping timekeeping : temp) {
                if (timekeeping.getTotal_time() == null) {
                    retired = true;
                    break;
                }
                total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
            }
            if (retired == true) {
                continue;
            }
            Penalty penalty = penaltyService.getPenaltybyid(number);
            total = total.plusNanos(penalty.getTime().toNanoOfDay());
            overall.add(new Overall(number,names.get(n), total,categoryl.get(n),car_class.get(n)));
            n++;
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
        LocalTime first=overall.get(0).getTime();
        LocalTime prev=overall.get(0).getTime();
        for(int y=0;y<overall.size();y++){
            String difftoPrevS;
            String difftoFirstS;
            if(y!=0) {
                Overall temp=overall.get(y);
                LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                difftoPrev=temp.getTime().minusNanos(prev.toNanoOfDay());
                difftoFirst=temp.getTime().minusNanos(first.toNanoOfDay());
                if(difftoPrev.getHour()==0){
                    int nano=difftoPrev.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoPrev.getMinute()==0){
                        difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoPrevS=difftoPrev.toString();
                }
                if(difftoFirst.getHour()==0){
                    int nano=difftoFirst.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoFirst.getMinute()==0){
                        difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoFirstS=difftoFirst.toString();
                }
                prev=temp.getTime();
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
            else{
                difftoPrevS="+00:00";
                difftoFirstS="+00:00";
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
        }
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generatebycategoryclass(overall, competitorService,"Category", category);
        return overall;
    }

    public List<Overall> OverallClassificationByStage(Long stage_id) {
        if (specialStageService.getSpecialStageById(stage_id).isEmpty()) {
            throw new EntityNotFoundException("The special stage does not exist");
        }
        List<Competitor> competitors = competitorService.getCompetitors();
        List <Long> numbers = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> category = new ArrayList<>();
        List<String> car_class = new ArrayList<>();
        for (Competitor competitor : competitors) {
            numbers.add(competitor.getCo_number());
            names.add(competitor.getDriver()+"-"+competitor.getCodriver());
            category.add(competitor.getCategory());
            car_class.add(competitor.getCar_class());
        }
        List <Overall> overall = new ArrayList<>();
        int n=0;
        for (Long number : numbers) {
            List<TimeKeeping>temp = timeKeepingRepository.findByIdCompetitorid(number);
            if (temp.get(temp.size()-1).getId().getSpecialstageid() >= stage_id && temp.get(temp.size()-1).getFinish_time() != null) {
                LocalTime total = LocalTime.of(0, 0, 0, 0);
                for (TimeKeeping timekeeping : temp) {
                    if (timekeeping.getId().getSpecialstageid() > stage_id) {
                       break;
                    }
                    total = total.plusNanos(timekeeping.getTotal_time().toNanoOfDay());
                }
                Penalty penalty = penaltyService.getPenaltybyid(number);
                total = total.plusNanos(penalty.getTime().toNanoOfDay());
                overall.add(new Overall(number,names.get(n), total,category.get(n),car_class.get(n)));
                n++;
            }

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
        LocalTime first=overall.get(0).getTime();
        LocalTime prev=overall.get(0).getTime();
        for(int y=0;y<overall.size();y++){
            String difftoPrevS;
            String difftoFirstS;
            if(y!=0) {
                Overall temp=overall.get(y);
                LocalTime difftoPrev=LocalTime.of(0, 0, 0, 0);
                LocalTime difftoFirst=LocalTime.of(0, 0, 0, 0);
                difftoPrev=temp.getTime().minusNanos(prev.toNanoOfDay());
                difftoFirst=temp.getTime().minusNanos(first.toNanoOfDay());
                if(difftoPrev.getHour()==0){
                    int nano=difftoPrev.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoPrev.getMinute()==0){
                        difftoPrevS=difftoPrev.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoPrevS=difftoPrev.getMinute()+":"+difftoPrev.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoPrevS=difftoPrev.toString();
                }
                if(difftoFirst.getHour()==0){
                    int nano=difftoFirst.getNano();
                    while(nano>1000){
                        nano=nano/10;
                    }
                    if(difftoFirst.getMinute()==0){
                        difftoFirstS=difftoFirst.getSecond()+"."+nano+"s";
                    }
                    else{
                        difftoFirstS=difftoFirst.getMinute()+":"+difftoFirst.getSecond()+"."+nano+"m";
                    }
                }
                else{
                    difftoFirstS=difftoFirst.toString();
                }
                prev=temp.getTime();
                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
            else{
                difftoPrevS="+00:00";
                difftoFirstS="+00:00";

                overall.get(y).setPrev(difftoPrevS);
                overall.get(y).setFirst(difftoFirstS);
            }
        }
        System.out.println(overall.get(1).getPrev());
        PdfGenerator pdfGenerator = new PdfGenerator();
        pdfGenerator.generateoverallbystage(overall, competitorService,specialStageService.getSpecialStageById(stage_id).get());
        return overall;
    }
}
