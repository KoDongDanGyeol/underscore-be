package com.kodong.underscore.map.data;

import com.kodong.underscore.map.entity.ServiceIndustry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자를 생성
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 생성
@Builder // 클래스 레벨에서 빌더 패턴을 적용
public class ServiceIndustryDTO {
    private String serviceIndustryCode;
    private String serviceIndustryCodeName;



    public ServiceIndustry convertToServiceIndustry(ServiceIndustryDTO dto){
        return ServiceIndustry.builder()
                .dto(dto)
                .build();
    }
}
