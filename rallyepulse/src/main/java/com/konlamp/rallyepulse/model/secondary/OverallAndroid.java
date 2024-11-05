package com.konlamp.rallyepulse.model.secondary;

import java.time.LocalTime;

public class OverallAndroid {
    private Long co_number;
    private LocalTime time;
    private String name;

    public Long getCo_number() {
        return co_number;
    }

    public void setCo_number(Long co_number) {
        this.co_number = co_number;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OverallAndroid(Long co_number, LocalTime time, String name) {
        this.co_number = co_number;
        this.time = time;
        this.name = name;
    }
}
