package com.konlamp.rallyepulse.repository;

import com.konlamp.rallyepulse.model.Category;
import com.konlamp.rallyepulse.model.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {


}