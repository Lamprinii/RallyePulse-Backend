package com.konlamp.rallyepulse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carclasses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarClass {

    @Id
    private String carclass;
}
