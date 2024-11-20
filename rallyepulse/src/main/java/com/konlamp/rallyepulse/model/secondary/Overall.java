package com.konlamp.rallyepulse.model.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Getter
@Setter
@RequiredArgsConstructor
public class Overall {
    private final Long co_number;
    private final String name;
    private final LocalTime time;
    private final String category;
    private final String car_class;
    private String prev;
    private String first;
    private LocalTime penalty;
}
