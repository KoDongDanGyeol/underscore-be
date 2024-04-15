package com.kodong.underscore.map.data.ixqq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.IndexQuarterlyQuotient;
import lombok.Data;

@Data
public class Ixqq {

    @JsonProperty("STDR_YYQU_CD")
    private String standardYearQuarterCode;

    @JsonProperty("ADSTRD_CD")
    private String adstrdCode;

    @JsonProperty("ADSTRD_CD_NM")
    private String adstrdCodeName;

    @JsonProperty("TRDAR_CHNGE_IX")
    private String trdarChngeIx;

    @JsonProperty("TRDAR_CHNGE_IX_NM")
    private String trdarChngeIxNm;

    @JsonProperty("OPR_SALE_MT_AVRG")
    private int oprSaleMtAvrg;

    @JsonProperty("CLS_SALE_MT_AVRG")
    private int clsSaleMtAvrg;

    @JsonProperty("SU_OPR_SALE_MT_AVRG")
    private int suOprSaleMtAvrg;

    @JsonProperty("SU_CLS_SALE_MT_AVRG")
    private int suClsSaleMtAvrg;

    public IndexQuarterlyQuotient convertToIndxQuarterlyQuotient(AdministrativeDistrict dong, Ixqq ixqq){
        return IndexQuarterlyQuotient.builder()
                .dong(dong)
                .indexQ(ixqq)
                .build();
    }

}
