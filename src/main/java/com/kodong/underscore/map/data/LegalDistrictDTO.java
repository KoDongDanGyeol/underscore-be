package com.kodong.underscore.map.data;

import com.kodong.underscore.map.entity.LegalDistrict;
import lombok.Data;

@Data
public class LegalDistrictDTO {

    // 시,도
    private String siDo;

    // 시,군,구
    private String siGunGu;

    // 행정구역 명
    private String administrativeDistrictName;

    // 행정동
    private String administrativeDong;

    // 법정동
    private String legalDong;

    // 행정구역 분류
    private String administrativeClassification;

    // 행정기관 코드
    private String administrativeCode;

    // 행정기관 생성일
    private String administrativeOrganizationCreationDate;

    // 법정동 코드
    private String legalDistrictCode;

    // 행정구역 영문명
    private String administrativeDistrictEnglishName;

    public LegalDistrict convertToLegalDistrict(LegalDistrictDTO dto){
        return LegalDistrict.builder()
                .dto(dto)
                .build();

    }
}
