package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.ServiceIndustry;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceIndustryRepository extends JpaRepository<ServiceIndustry, Long> {

    Optional<ServiceIndustry> findByServiceIndustryCode(String serviceIndustryCode);
    List<ServiceIndustry> findByServiceIndustryCodeContaining(String code, Sort sort);
}
