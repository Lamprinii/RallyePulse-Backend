package com.konlamp.rallyepulse.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TimeKeepingid implements Serializable {
    private Long competitorid;
    private Long specialstageid;


}
