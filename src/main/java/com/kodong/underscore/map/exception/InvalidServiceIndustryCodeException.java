package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public class InvalidServiceIndustryCodeException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidServiceIndustryCodeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
