package com.konlamp.rallyepulse.model.secondary;

import com.konlamp.rallyepulse.model.TimeKeepingid;
import com.konlamp.rallyepulse.model.converters.LocalTimeAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
@Setter
public class TimekeepingExport {

    private TimeKeepingid id;

    private LocalTime start_time;

    private LocalTime finish_time;

    private LocalTime total_time;

    private String Competitor;

    private String category;

    private String car_class;

    private String diffToFirst;

    private String diffToPrevious;
}
