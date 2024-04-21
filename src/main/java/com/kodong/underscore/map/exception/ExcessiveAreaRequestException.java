package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public class ExcessiveAreaRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public ExcessiveAreaRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
