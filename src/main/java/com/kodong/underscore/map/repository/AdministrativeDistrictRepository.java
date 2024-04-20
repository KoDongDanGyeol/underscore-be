package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministrativeDistrictRepository extends JpaRepository<AdministrativeDistrict, Long> {
    Optional<AdministrativeDistrict> findByAdministrativeCode(String administrativeCode);
    Optional<AdministrativeDistrict> findByAdministrativeClassification(String administrativeClassification);

    // 해당 범위 내에 존재하는 행정동 반환
    @Query("SELECT ad FROM AdministrativeDistrict ad WHERE ad.xLongitude BETWEEN :minXLongitude AND :maxXLongitude AND ad.yLatitude BETWEEN :minYLatitude AND :maxYLatitude")
    List<AdministrativeDistrict> findByXLongitudeBetweenAndYLatitudeBetween(double minXLongitude, double maxXLongitude, double minYLatitude, double maxYLatitude);


}
