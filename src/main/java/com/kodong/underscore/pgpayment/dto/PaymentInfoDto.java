package com.kodong.underscore.pgpayment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PaymentInfoDto {
    private String orderId;
    private String orderName;
    private String approvedAt;
    private String paymentMethod;
    private String status;
    private int amount;

}
