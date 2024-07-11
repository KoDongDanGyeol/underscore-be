package com.kodong.underscore.pgpayment.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TossPayApprove {
    private String mId;
    private String lastTransactionKey;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private int taxExemptionAmount;
    private String status;
    private String requestedAt;
    private String approvedAt;
    private boolean cultureExpense;
    private String method;
    private int totalAmount;
    private int balanceAmount;
    private int suppliedAmount;
    private int vat;
    private int taxFreeAmount;
    private CardInfo card;
    private ReceiptInfo receipt;
    private MobilePhoneInfo mobilePhone;
    private FailureInfo failure;



    @Data
    public static class CardInfo {
        private String issuerCode;
        private String acquirerCode;
        private String number;
        private int amount;
        private int installmentPlanMonths;
        private boolean isInterestFree;
        private String interestPayer;
        private String approveNo;
        private boolean useCardPoint;
        private String cardType;
        private String acquireStatus;


    }

    @Data
    public static class ReceiptInfo{
        private String url;
    }

    @Data
    public static class MobilePhoneInfo{
        private MobilePhoneDetail customerMobilePhone;
        private String settlementStatus;
        private String receiptUrl;

        @Data
        public static class MobilePhoneDetail{
            private String plain;//전체 휴대폰 번호
            private String masking;//중간 4자리 마스킹 된 휴대폰 번호
        }
    }

    @Data
    public static class FailureInfo{
        private String code;
        private String message;
    }



}
