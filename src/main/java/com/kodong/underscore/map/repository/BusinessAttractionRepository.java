package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.BusinessAttraction;
import com.kodong.underscore.map.entity.BusinessAttractionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessAttractionRepository extends JpaRepository<BusinessAttraction, BusinessAttractionId> {
}
