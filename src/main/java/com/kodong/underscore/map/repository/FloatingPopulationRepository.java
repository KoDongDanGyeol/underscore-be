package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.FloatingPopulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FloatingPopulationRepository extends JpaRepository<FloatingPopulation,Long> {
    List<FloatingPopulation> findAllByStandardYearQuarterCodeOrderByTotFlpopCoAsc(String standardYearQuarterCode);
    Page<FloatingPopulation> findAll(Pageable pageable);
    Optional<FloatingPopulation> findByStandardYearQuarterCodeAndAdministrativeDistrict(String standardYearQuarterCode, AdministrativeDistrict administrativeDistrictCode);

}
