package com.kodong.underscore.map.entity;


import com.kodong.underscore.map.data.AdministrativeDistrictDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdministrativeDistrict {

    @Id
    @GeneratedValue
    private Long id;
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



    @Builder
    public AdministrativeDistrict(AdministrativeDistrictDTO dto){
        this.siDo = dto.getSiDo();
        this.siGunGu = dto.getSiGunGu();
        this.administrativeCode = dto.getAdministrativeCode();
        this.administrativeOrganizationCreationDate = dto.getAdministrativeOrganizationCreationDate();
        this.eupMyeonDong = dto.getEupMyeonDong();
        this.administrativeOrganizationCancellationDate = dto.getAdministrativeOrganizationCancellationDate();
    }

}
