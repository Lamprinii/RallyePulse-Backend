package com.konlamp.rallyepulse.repository;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.SpecialStage;
import com.konlamp.rallyepulse.model.StartList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartListRepository extends JpaRepository<StartList, Long> {

    public List<StartList> findAllByOrderByPositionAsc();
    public StartList findByCompetitor(Competitor competitor);

    public StartList deleteStartListByCompetitor(Competitor competitor);

}
