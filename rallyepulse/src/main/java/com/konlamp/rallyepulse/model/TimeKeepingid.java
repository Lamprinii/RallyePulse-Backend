package com.konlamp.rallyepulse.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeKeepingid implements Serializable {
    private Long competitorid;
    private Long specialstageid;


}
