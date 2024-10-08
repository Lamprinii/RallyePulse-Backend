package com.konlamp.rallyepulse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "startList")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StartList {




        @OneToOne(optional = false)
        @JoinColumn(name = "id_co_number", nullable = false)
        private Competitor competitor;
        @Column
        private LocalTime time;
        @Id
        @Column(unique = true, nullable = false)
        private int position;


}

