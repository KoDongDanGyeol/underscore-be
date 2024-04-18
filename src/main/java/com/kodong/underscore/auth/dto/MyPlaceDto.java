package com.kodong.underscore.auth.dto;

import lombok.Setter;

@Setter
public class MyPlaceDto {
    // 시,도
    private String siDo;

    // 시,군,구
    private String siGunGu;

    // 읍,면,동
    private String eupMyeonDong;
}
