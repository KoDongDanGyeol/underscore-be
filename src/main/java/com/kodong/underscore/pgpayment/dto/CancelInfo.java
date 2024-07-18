package com.kodong.underscore.pgpayment.dto;

import lombok.Getter;

@Getter
public class CancelInfo {
    private String cancelReason;
    private String paymentKey;
}
