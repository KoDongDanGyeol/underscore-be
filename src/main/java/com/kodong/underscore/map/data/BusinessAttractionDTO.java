package com.kodong.underscore.map.data;

import lombok.Builder;
import lombok.Getter;

/**
 * 특정 행정동의 개업매력도 점수를 넘겨 줍니다.
 */
@Getter
@Builder
public class BusinessAttractionDTO {
    private Long id;
    private String administrativeDistrictName; //행정동 이름
    private int[] businessAttractionScores; // 개업매력도 분야별 점수
    private int totalScore; //총 점
    private double xLongitude;
    private double yLatitude;

}
