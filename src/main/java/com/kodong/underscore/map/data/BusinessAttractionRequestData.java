package com.kodong.underscore.map.data;

import lombok.Data;

import java.util.List;

/**
 * 클라이언트에서 개업 매력도 점수를 요청할 때 Body 와 매핑되는 클래스입니다.
 */
@Data
public class BusinessAttractionRequestData {
    private List<String> legalDistrictCode;
    private String serviceIndustryCode;


}
