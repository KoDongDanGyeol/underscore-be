package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.LegalDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LegalDistrictRepository extends JpaRepository<LegalDistrict, Long> {
    Optional<LegalDistrict> findByLegalDistrictCodeAndAdministrativeCode(String legalDistrictCode, String administrativeCode);

    // 법정동을 찾아서 행정동코드 넘겨주는 함수
    @Query("SELECT ld.administrativeCode FROM LegalDistrict ld WHERE ld.legalDistrictCode = :legalDistrictCode")
    Optional<String> findAdministrativeCodeByLegalDistrictCode(@Param("legalDistrictCode") String legalDistrictCode);
}
