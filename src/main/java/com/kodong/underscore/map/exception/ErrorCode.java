package com.kodong.underscore.map.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND("Not_Found", "요청한 리소스를 찾을 수 없습니다."),
    EXCESSIVE_AREA_REQUEST("Excessive_Area_Request", "너무 넓은 범위에 대한 요청입니다."),
    PAYMENT_REQUIRED("Payment_Required", "결제가 필요한 서비스입니다."),
    INVALID_INPUT("Invalid_Input", "입력 값이 유효하지 않습니다."),
    INVALID_SERVICE_INDUSTRY_CODE("Invalid_Service_Industry_Code", "서비스 업종 분야 코드가 적절하지 않습니다."),
    NO_MATCHING_SERVICE_INDUSTRY("No_Matching_Service_Industry", "DB에 매칭되는 서비스 업종 분야 코드가 존재하지 않습니다."),
    UNAUTHORIZED("Unauthorized", "인증이 필요합니다."),
    LOGIN_REQUIRED("Login_Required", "로그인이 필요합니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


}
