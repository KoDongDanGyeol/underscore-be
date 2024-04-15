package com.kodong.underscore.map.data.stor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.ServiceIndustry;
import com.kodong.underscore.map.entity.Store;
import lombok.Data;

@Data
public class Stor {
    @JsonProperty("STDR_YYQU_CD")
    private String standardYearQuarterCode;

    @JsonProperty("ADSTRD_CD")
    private String adstrdCode;

    @JsonProperty("ADSTRD_CD_NM")
    private String adstrdCodeName;

    @JsonProperty("SVC_INDUTY_CD")
    private String serviceIndustryCode;

    @JsonProperty("SVC_INDUTY_CD_NM")
    private String serviceIndustryCodeName;

    @JsonProperty("STOR_CO")
    private int storeCount;

    @JsonProperty("SIMILR_INDUTY_STOR_CO")
    private int similarIndustryStoreCount;

    @JsonProperty("OPBIZ_RT")
    private int openingBusinessRate;

    @JsonProperty("OPBIZ_STOR_CO")
    private int openingBusinessStoreCount;

    @JsonProperty("CLSBIZ_RT")
    private int closingBusinessRate;

    @JsonProperty("CLSBIZ_STOR_CO")
    private int closingBusinessStoreCount;

    @JsonProperty("FRC_STOR_CO")
    private int franchiseStoreCount;

    public Store convertToStore(AdministrativeDistrict dong, ServiceIndustry industry, Stor store){
        return Store.builder()
                .dong(dong)
                .serviceIndustry(industry)
                .store(store)
                .build();
    }
}
