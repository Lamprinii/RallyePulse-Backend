package com.konlamp.rallyepulse.model.secondary;

import com.konlamp.rallyepulse.model.TimeKeepingid;
import com.konlamp.rallyepulse.model.converters.LocalTimeAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
public class TimeKeepingAndroid {

        private String name;
        private TimeKeepingid id;

        private String start_time;

        private String finish_time;

        private String total_time;

        public TimeKeepingid getId() {
            return id;
        }

        public void setId(TimeKeepingid id) {
            this.id = id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        public TimeKeepingAndroid(TimeKeepingid id, String start_time, String finish_time, String total_time, String name) {
            this.id = id;
            this.start_time = start_time;
            this.finish_time = finish_time;
            this.total_time = total_time;
            this.name = name;
        }

    }


