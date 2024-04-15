package com.kodong.underscore.map.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BusinessAttractionId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrative_dong_id",referencedColumnName = "id")
    private AdministrativeDistrict administrativeDistrictId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_industry_id",referencedColumnName = "id")
    private ServiceIndustry serviceIndustryId;
    private String standardYearQuarterCode;

}
