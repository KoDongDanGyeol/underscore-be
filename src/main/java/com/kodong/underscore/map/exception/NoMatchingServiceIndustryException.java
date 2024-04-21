package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public class NoMatchingServiceIndustryException extends RuntimeException {
    private final ErrorCode errorCode;

    public NoMatchingServiceIndustryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
