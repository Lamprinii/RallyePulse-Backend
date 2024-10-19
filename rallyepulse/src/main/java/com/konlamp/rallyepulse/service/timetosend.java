package com.konlamp.rallyepulse.service;

import com.konlamp.rallyepulse.model.TimeKeepingid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class timetosend{
    private final TimeKeepingid id;
    private final String start_time;
    private final String finish_time;
    private final String total_time;
    
}
