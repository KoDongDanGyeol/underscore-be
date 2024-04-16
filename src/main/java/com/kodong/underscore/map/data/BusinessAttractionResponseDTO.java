package com.kodong.underscore.map.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 *  개업매력도 점수를 넘겨주는 DTO 여러 개의 동에 대한 점수 넘겨줍니다.
 */
@Data
@Builder
public class BusinessAttractionResponseDTO {
    private int count;
    private boolean includesUnserviceableAreas;
    private String[] labels; // 다각형 그래프의 각 축의 제목,이름
    private List<BusinessAttractionDTO> businessAttractions;


}
