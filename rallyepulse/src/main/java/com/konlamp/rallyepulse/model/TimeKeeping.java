package com.konlamp.rallyepulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
@Entity
@Table(name = "timekeeping")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeKeeping {

    @EmbeddedId
    private TimeKeepingid id;
    @Column
    private LocalTime start_time;
    @Column
    private LocalTime finish_time;
    @Column
    private LocalTime total_time;

    public TimeKeeping(TimeKeepingid id, LocalTime start_time) {
        this.id = id;
        this.start_time = start_time;
    }
}

