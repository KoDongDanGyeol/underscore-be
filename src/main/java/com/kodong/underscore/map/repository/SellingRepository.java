package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.Selling;
import com.kodong.underscore.map.entity.ServiceIndustry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellingRepository extends JpaRepository<Selling,Long> {
    List<Selling> findAllByServiceIndustryAndStandardYearQuarterCodeOrderByThsmonSelngAmt(ServiceIndustry serviceIndustry, String standardYearQuarterCode);
    Page<Selling> findAll(Pageable pageable);
    Optional<Selling> findByStandardYearQuarterCodeAndAdministrativeDistrictAndServiceIndustry(
            String standardYearQuarterCode, AdministrativeDistrict administrativeDistrict, ServiceIndustry serviceIndustry
    );
}
