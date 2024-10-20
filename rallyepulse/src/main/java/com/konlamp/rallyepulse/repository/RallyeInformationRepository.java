package com.konlamp.rallyepulse.repository;

import com.konlamp.rallyepulse.model.Competitor;
import com.konlamp.rallyepulse.model.RallyeInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RallyeInformationRepository extends JpaRepository<RallyeInformation, Long> {


}
