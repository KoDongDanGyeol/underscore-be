package com.kodong.underscore.map.entity;


import com.kodong.underscore.map.data.ServiceIndustryDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceIndustryCode;
    private String serviceIndustryCodeName;

    @Builder
    public ServiceIndustry(ServiceIndustryDTO dto){
        this.serviceIndustryCode = dto.getServiceIndustryCode();
        this.serviceIndustryCodeName = dto.getServiceIndustryCodeName();
    }

    public ServiceIndustryDTO convertToServiceIndustryDTO(ServiceIndustry serviceIndustry){
        return ServiceIndustryDTO.builder()
                .serviceIndustryCode(serviceIndustry.getServiceIndustryCode())
                .serviceIndustryCodeName(serviceIndustry.getServiceIndustryCodeName())
                .build();
    }

}
