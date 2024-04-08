package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.IncomeConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeConsumptionRepository extends JpaRepository<IncomeConsumption,Long> {
}
