package com.kodong.underscore.map.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataConfig {
    DistrictData("AdministrativeDistrictData.csv",new String[]{
            "siDo", "siGunGu", "administrativeDong",
            "administrativeClassification","administrativeCode",
            "administrativeOrganizationCreationDate"
    }),

    DistrictAddressData("AdministrativeDistrictAddressData.csv",new String[]{
            "id", "siDo", "siGunGu", "administrativeDong", "postNumber",
            "address"
    }),

    LegalDistrictData("LegalDistrictData.csv",new String[]{
            "siDo", "siGunGu",
            "administrativeDistrictName" ,"administrativeDong", "legalDong",
            "administrativeClassification","administrativeCode", "administrativeOrganizationCreationDate",
            "legalDistrictCode", "administrativeDistrictEnglishName"
    }),

    ServiceIndustry("ServiceIndustryData.csv",new String[]{
            "serviceIndustryCode",
            "serviceIndustryCodeName"
    });

    private final String csvFileName;
    private final String[] columnNames;
}
