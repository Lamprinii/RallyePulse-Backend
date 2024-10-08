package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.PdfGenerator;
import com.konlamp.rallyepulse.model.StartList;
import com.konlamp.rallyepulse.model.secondary.Overall;
import com.konlamp.rallyepulse.repository.StartListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StartListService {

    public final StartListRepository startListRepository;
    public final CompetitorService competitorService;
    public final TimeKeepingService timeKeepingService;

    public List<StartList> createBeginStartList(LocalTime starttime) {
        try {
            List<Competitor> competitors = competitorService.getCompetitors();
            ArrayList<StartList> list=new ArrayList<StartList>();
            int i=1;
            for(Competitor competitor : competitors) {
                System.out.println(competitor.getDriver());
                list.add(new StartList(competitor,starttime,i));
                starttime=starttime.plusMinutes(2);
                i++;
            }

            startListRepository.saveAll(list);
            return getStartList();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

    }

    public List<StartList> getStartList() {
        return startListRepository.findAllByOrderByPositionAsc();
    }

    public void deleteStartList() {
        startListRepository.deleteAll();
    }

    public List<StartList> changeTime(LocalTime starttime,Long co_number) {
        Optional<Competitor> optional_competitor = competitorService.getCompetitorbyid(co_number);
        if(optional_competitor.isPresent()) {
            Competitor competitor = optional_competitor.get();
            StartList startList=startListRepository.findByCompetitor(competitor);
            startList.setTime(starttime);
            startListRepository.save(startList);
            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generateStartList(getStartList(),"LEG 1",LocalDate.now());
            return getStartList();
        }
        else {
            throw new EntityNotFoundException("Competitor not found");
        }
    }

    public void pdf(LocalDate date,String leg){
        PdfGenerator pdf=new PdfGenerator();
        pdf.generateStartList(getStartList(),leg,date);
    }

    public List<StartList> new_Leg(LocalTime starttime){
        deleteStartList();
        List<Overall> overalls=timeKeepingService.OverallClassification();
        List<Competitor> BasedOnOveralls=new ArrayList<>();
        for(Overall overall : overalls) {
            Competitor competitor=competitorService.getCompetitorbyid(overall.getCo_number()).get();
            BasedOnOveralls.add(competitor);
        }
        ArrayList<StartList> list=new ArrayList<StartList>();
        int i=1;
        for(Competitor competitor : BasedOnOveralls) {
            StartList startList=new StartList(competitor,starttime,i);
            list.add(startList);
            starttime=starttime.plusMinutes(2);
            i++;
        }
        startListRepository.saveAll(list);
        return startListRepository.findAllByOrderByPositionAsc();
    }

    public List<StartList> changePosistion(Long co_number,int position) throws Exception{
        List<StartList> list=getStartList();
        LocalTime starttime=list.get(0).getTime();
        deleteStartList();
        StartList toRemove;
        int removeindex=-1;
        int delpos=-1;
        for(int i=0;i<list.size();i++) {
            if(list.get(i).getCompetitor().getCo_number()==co_number) {
                removeindex=i;
                break;
            }
        }
        if(delpos==position){
            return getStartList();
        }
        StartList change=list.remove(removeindex);
        list.add(position-1,change);
        LocalTime prevStarttime=LocalTime.now();
        for(int i=0;i<list.size();i++) {
            if(i==0){
                prevStarttime=starttime;
                list.get(i).setTime(starttime);
            }
            else {
                if(i<position-1||i>position) {
                    starttime=starttime.plusMinutes(LocalTime.ofNanoOfDay(list.get(i).getTime().toNanoOfDay()-prevStarttime.toNanoOfDay()).getMinute());
                    prevStarttime=starttime;
                }
                else {
                    starttime=prevStarttime.plusMinutes(2);
                    prevStarttime=starttime;

                }
            }
            list.get(i).setPosition(i+1);
        }
        startListRepository.saveAll(list);
        return getStartList();
    }

    public void deleteById(Long id) {
        Optional<Competitor> optional_competitor = competitorService.getCompetitorbyid(id);
        if(!optional_competitor.isPresent()){
            throw new EntityNotFoundException("Competitor not found");
        };
        startListRepository.deleteStartListByCompetitor(optional_competitor.get());
    }

}
