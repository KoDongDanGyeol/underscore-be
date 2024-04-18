package com.kodong.underscore.map.data;

import lombok.Data;

@Data
public class AdministrativeDistrictAddressDTO {

    // 연 번
    private int id;

    // 시,도
    private String siDo;

    // 시,군,구
    private String siGunGu;

    // 행정동
    private String administrativeDong;

    // 우편번호
    private int postNumber;

    // 주소
    private String address;

}
