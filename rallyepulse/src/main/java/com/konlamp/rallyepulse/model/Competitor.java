package com.konlamp.rallyepulse.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.time.LocalDateTime;

@Entity
@Table(name = "competitors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Competitor {

        @Id
        private Long co_number;

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
