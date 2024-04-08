package com.kodong.underscore.map.data;

import com.kodong.underscore.map.entity.AdministrativeDistrict;
import lombok.Data;

@Data
public class AdministrativeDistrictDTO {

    // 행정기관 코드
    private String administrativeCode;

    // 시,도
    private String siDo;

    // 시,군,구
    private String siGunGu;

    // 읍,면,동
    private String eupMyeonDong;

    // 행정기관 생성일
    private String administrativeOrganizationCreationDate;

    // 행정기관 생성일
    private String administrativeOrganizationCancellationDate;

    public AdministrativeDistrict convertToAdministrativeDistrict(AdministrativeDistrictDTO dto){
        return AdministrativeDistrict.builder()
                .dto(dto)
                .build();
    }
}
