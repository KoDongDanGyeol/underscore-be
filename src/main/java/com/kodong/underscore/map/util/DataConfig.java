package com.kodong.underscore.map.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataConfig {
    DistrictData("AdministrativeDistrictData.csv",new String[]{
            "administrativeCode",
            "siDo", "siGunGu", "eupMyeonDong",
            "administrativeOrganizationCreationDate","administrativeOrganizationCancellationDate"
    }),

    ServiceIndustry("ServiceIndustryData.csv",new String[]{
            "serviceIndustryCode",
            "serviceIndustryCodeName"
    });

    private final String csvFileName;
    private final String[] columnNames;
}
