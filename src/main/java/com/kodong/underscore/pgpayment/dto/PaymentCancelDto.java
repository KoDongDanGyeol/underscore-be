package com.kodong.underscore.pgpayment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PaymentCancelDto {
    private String cancelReason;
    private String canceledAtDate;
    private String canceledAtTime;
    private int cancelAmount;
    private String cancelStatus;

}
