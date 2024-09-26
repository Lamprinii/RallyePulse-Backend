package com.konlamp.rallyepulse.model.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FinishTime {
    Long co_number;
    Long stage;
    int hour;
    int minute;
    int second;
    int nano;
    int decimal;

}
