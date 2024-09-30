package com.konlamp.rallyepulse.model.secondary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class Overall {
    private Long co_number;
    private LocalTime time;
}
