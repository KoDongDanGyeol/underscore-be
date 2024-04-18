package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.LegalDistrictDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    @Builder
    public LegalDistrict(LegalDistrictDTO dto){
        this.siDo = dto.getSiDo();
        this.siGunGu = dto.getSiGunGu();
        this.administrativeDistrictName = dto.getAdministrativeDistrictName();
        this.administrativeDong = dto.getAdministrativeDong();
        this.legalDong = dto.getLegalDong();
        this.administrativeClassification = dto.getAdministrativeClassification();
        this.administrativeCode = dto.getAdministrativeCode();
        this.administrativeOrganizationCreationDate = dto.getAdministrativeOrganizationCreationDate();
        this.legalDistrictCode = dto.getLegalDistrictCode();
        this.administrativeDistrictEnglishName = dto.getAdministrativeDistrictEnglishName();
    }


}
