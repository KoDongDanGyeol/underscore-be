package com.kodong.underscore.pgpayment.repository;

import com.kodong.underscore.pgpayment.entity.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PaymentsRepository extends JpaRepository<TossPayment,Long>, JpaSpecificationExecutor<TossPayment> {



  //  Page<TossPayment> findByUserId(Long userId, Pageable pageable);

    TossPayment findByUserIdAndPaymentKey(Long userId, String paymentKey);

    int countByUserId(Long userId);
}
