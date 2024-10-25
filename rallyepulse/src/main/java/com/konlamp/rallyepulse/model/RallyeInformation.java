package com.konlamp.rallyepulse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rallyeinformation")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RallyeInformation {
    @Id
    private Long id;
    private String title;
    private String date;
    private String city;
    private boolean results;
}
