package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.IndexQuarterlyQuotient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexQuarterlyQuotientRepository extends JpaRepository<IndexQuarterlyQuotient,Long> {
    Page<IndexQuarterlyQuotient> findAll(Pageable pageable);
    Optional<IndexQuarterlyQuotient> findByStandardYearQuarterCodeAndAdministrativeDistrict(String standardYearQuarterCode, AdministrativeDistrict district);
    Page<IndexQuarterlyQuotient> findByStandardYearQuarterCode(String standardYearQuarterCode, Pageable pageable);
}