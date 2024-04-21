package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public class PaymentRequiredException extends RuntimeException {
    private final ErrorCode errorCode;

    public PaymentRequiredException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
