package com.konlamp.rallyepulse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDateTime;

@Entity
@Table(name = "competitors")
@AllArgsConstructor
@NoArgsConstructor
public class Competitor {



        @Id
        private Long co_number;

    public Long getCo_number() {
        return co_number;
    }

    public void setCo_number(Long co_number) {
        this.co_number = co_number;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCodriver() {
        return codriver;
    }

    public void setCodriver(String codriver) {
        this.codriver = codriver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    @Column
        private String driver;
        @Column
        private String codriver;
        @Column
        private String email;
        @Column
        private String telephone;
        @Column
        private String vehicle;
        @Column
        private String category;
        @Column
        private String car_class;


}
