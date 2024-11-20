package com.konlamp.rallyepulse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specialstages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecialStage {

    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String distance;

}
