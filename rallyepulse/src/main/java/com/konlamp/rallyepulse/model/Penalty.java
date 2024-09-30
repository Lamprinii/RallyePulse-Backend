package com.konlamp.rallyepulse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
@Entity
@Table(name = "penalties")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Penalty {
    @Id
    private Long co_number;

    @Column
    LocalTime time;
}
