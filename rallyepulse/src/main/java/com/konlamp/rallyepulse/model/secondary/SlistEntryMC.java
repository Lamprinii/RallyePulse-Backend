package com.konlamp.rallyepulse.model.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class SlistEntryMC {

    private Long hours;
    private Long minutes;
    private Long month;
    private Long day;
    private Long id;
    private String tc;




}
