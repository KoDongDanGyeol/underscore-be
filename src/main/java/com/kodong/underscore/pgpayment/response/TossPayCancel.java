package com.kodong.underscore.pgpayment.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TossPayCancel {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private Card card;
    private Cancel[] cancels;




    @Data
    public static class Card{
        private int amount;
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int installmentPlanMonths;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
        private boolean isInterestFree;
        private String interestPayer;

    }


    @Data
    public static class Cancel{
        private int cancelAmount;
        private String cancelReason;
        private int taxFreeAmount;
        private int  taxExemptionAmount;
        private int refundableAmount;
        private int easyPayDiscountAmount;
        private String canceledAt;
        private String transactionKey;
        private String receiptKey;
        private String cancelStatus;
        private String cancelRequestId;







    }
}
