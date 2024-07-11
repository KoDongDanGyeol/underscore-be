package com.kodong.underscore.pgpayment.repository;

import com.kodong.underscore.pgpayment.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberShipRepository extends JpaRepository<Membership,Long> {
    Membership findByAmount(int amount);
}
