package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.ResidentPopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentPopulationRepository extends JpaRepository<ResidentPopulation, Long> {
}
