package com.konlamp.rallyepulse.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.TimeKeepingid;
import com.konlamp.rallyepulse.model.converters.LocalTimeAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    public void SendNotification(TimeKeeping time) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (time.getFinish_time() != null) {
                String Message = mapper.writeValueAsString(new timetosend(time.getId(),time.getStart_time().toString(),time.getFinish_time().toString(),time.getTotal_time().toString()));
                messagingTemplate.convertAndSend("/stoptime/"+time.getId().getSpecialstageid()+"/updates", Message);
            } else {
                String Message = mapper.writeValueAsString(new timetosend(time.getId(),time.getStart_time().toString(),"",time.getTotal_time().toString()));
                messagingTemplate.convertAndSend("/stoptime/"+time.getId().getSpecialstageid()+"/updates", Message);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }



}
