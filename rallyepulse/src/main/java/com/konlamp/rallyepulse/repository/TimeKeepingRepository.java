package com.konlamp.rallyepulse.repository;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.model.TimeKeeping;
import com.konlamp.rallyepulse.model.TimeKeepingid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, TimeKeepingid> {

    List<TimeKeeping> findByIdCompetitorid(Long competitorid);
    List<TimeKeeping> findByIdSpecialstageid(Long specialstageid);


}
