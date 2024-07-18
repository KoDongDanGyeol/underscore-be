package com.kodong.underscore.pgpayment.request;

import lombok.Builder;
import lombok.Getter;

//결제 환불 시 필요한 내용
@Getter
@Builder
public class CancelUserInfo {
    private String cancelReason;
    private int cancelAmount;
}
