package com.konlamp.rallyepulse.repository;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.SpecialStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialStageRepository extends JpaRepository<SpecialStage, Long> {


}