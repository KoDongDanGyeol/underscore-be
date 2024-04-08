package com.kodong.underscore.map.repository;

import com.kodong.underscore.map.entity.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingRepository extends JpaRepository<Selling,Long> {
}
