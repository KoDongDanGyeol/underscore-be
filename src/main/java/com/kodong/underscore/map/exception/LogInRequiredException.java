package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public class LogInRequiredException extends RuntimeException {
    private final ErrorCode errorCode;

    public LogInRequiredException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
