package com.konlamp.rallyepulse.model;

import com.konlamp.rallyepulse.model.converters.LocalTimeAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serializable;
import java.time.LocalTime;
@Entity
@Table(name = "timekeeping")
@Getter
@Setter
public class TimeKeeping {

    @EmbeddedId
    private TimeKeepingid id;

    @Column
    private LocalTime start_time;

    @Column(name="finish_times")
    @Convert(converter = LocalTimeAttributeConverter.class)
    private LocalTime finish_time;

    @Column(name="total_times")
    @Convert(converter = LocalTimeAttributeConverter.class)
    private LocalTime total_time;


    public TimeKeeping(TimeKeepingid id, LocalTime start_time) {
        this.id = id;
        this.start_time = start_time;
    }

    public TimeKeeping() {

    }

    public TimeKeepingid getId() {
        return id;
    }

    public void setId(TimeKeepingid id) {
        this.id = id;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(LocalTime finish_time) {
        this.finish_time = finish_time;
    }

    public LocalTime getTotal_time() {
        return total_time;
    }

    public void setTotal_time(LocalTime total_time) {
        this.total_time = total_time;
    }

    public TimeKeeping(TimeKeepingid id, LocalTime start_time, LocalTime finish_time, LocalTime total_time) {
        this.id = id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.total_time = total_time;
    }
}

