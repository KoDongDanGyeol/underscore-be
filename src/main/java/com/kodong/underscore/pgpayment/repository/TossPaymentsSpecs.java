package com.kodong.underscore.pgpayment.repository;

import com.kodong.underscore.pgpayment.entity.TossPayment;
import org.springframework.data.jpa.domain.Specification;


public class TossPaymentsSpecs {
    public static Specification<TossPayment> hasUserId(Long userId){
        return(root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userId"),userId);
    }


    public static Specification<TossPayment> hasPaymentStatus(String paymentStatus){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paymentStatus"),paymentStatus);
    }





}
