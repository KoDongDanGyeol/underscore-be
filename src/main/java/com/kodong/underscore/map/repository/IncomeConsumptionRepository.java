package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.IncomeConsumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeConsumptionRepository extends JpaRepository<IncomeConsumption,Long> {
    List<IncomeConsumption> findAllByStandardYearQuarterCodeOrderByFoodExpenditureAmountAsc(String standardYearQuarterCode);
    Page<IncomeConsumption> findAll(Pageable pageable);

    Optional<IncomeConsumption> findByStandardYearQuarterCodeAndAdministrativeDistrict(String standardYearQuarterCode, AdministrativeDistrict district);
}
