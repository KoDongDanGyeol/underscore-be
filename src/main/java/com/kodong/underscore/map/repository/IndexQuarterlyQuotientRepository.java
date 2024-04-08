package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.IndexQuarterlyQuotient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexQuarterlyQuotientRepository extends JpaRepository<IndexQuarterlyQuotient,Long> {
}