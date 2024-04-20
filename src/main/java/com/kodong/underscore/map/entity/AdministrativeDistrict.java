package com.kodong.underscore.map.entity;


import com.kodong.underscore.map.data.AdministrativeDistrictDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdministrativeDistrict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 시,도
    private String siDo;

    // 시,군,구
    private String siGunGu;

    // 행정동
    private String administrativeDong;

    //행정구역 분류
    private String administrativeClassification;

    // 행정기관 코드
    private String administrativeCode;

    // 행정기관 생성일
    private String administrativeOrganizationCreationDate;

    // x 좌표, 경도
    private double xLongitude;

    // y 좌표, 위도
    private double yLatitude;

    @Builder
    public AdministrativeDistrict(AdministrativeDistrictDTO dto){
        this.siDo = dto.getSiDo();
        this.siGunGu = dto.getSiGunGu();
        this.administrativeDong = dto.getAdministrativeDong();
        this.administrativeClassification = dto.getAdministrativeClassification();
        this.administrativeCode = dto.getAdministrativeCode();
        this.administrativeOrganizationCreationDate = dto.getAdministrativeOrganizationCreationDate();
        this.xLongitude = dto.getXLongitude();
        this.yLatitude = dto.getYLatitude();
    }

    public String getFullAddress(){
        return siDo + " " +
                siGunGu + " " +
                administrativeDong;
    }

    public double updateLongitude(double longitude){
        this.xLongitude = longitude;
        return longitude;
    }

    public double updateLatitude(double latitude){
        this.yLatitude = latitude;
        return latitude;
    }

}
