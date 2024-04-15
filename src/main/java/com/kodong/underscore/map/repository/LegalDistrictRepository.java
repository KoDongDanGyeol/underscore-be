package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.LegalDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalDistrictRepository extends JpaRepository<LegalDistrict, Long> {
    Optional<LegalDistrict> findByLegalDistrictCode(String legalDistrictCode);
}
