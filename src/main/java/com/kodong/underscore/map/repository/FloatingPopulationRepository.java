package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.FloatingPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation,Long> {
}
