package com.kodong.underscore.map.handler;

import com.kodong.underscore.map.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LogInRequiredException.class)
    public ResponseEntity<Object> handleCustomException(LogInRequiredException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 상황에 따라 적절한 상태 코드 선택
                .body(Map.of(
                        "errorCode", ex.getErrorCode(),
                        "errorMessage", ex.getMessage()
                ));
    }

    @ExceptionHandler(ExcessiveAreaRequestException.class)
    public ResponseEntity<Object> handleExcessiveAreaRequestException(ExcessiveAreaRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "errorCode", ex.getErrorCode(),
                        "errorMessage", ex.getMessage()
                ));
    }

    @ExceptionHandler(PaymentRequiredException.class)
    public ResponseEntity<Object> handlePaymentRequiredException(PaymentRequiredException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "errorCode", ex.getErrorCode(),
                        "errorMessage", ex.getMessage()
                ));
    }

    @ExceptionHandler(NoMatchingServiceIndustryException.class)
    public ResponseEntity<Object> handleNoMatchingServiceIndustryException(NoMatchingServiceIndustryException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "errorCode", ex.getErrorCode(),
                        "errorMessage", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidServiceIndustryCodeException.class)
    public ResponseEntity<Object> handleInvalidServiceIndustryException(InvalidServiceIndustryCodeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "errorCode" , ex.getErrorCode(),
                        "errorMessage", ex.getMessage()
                ));
    }

}
