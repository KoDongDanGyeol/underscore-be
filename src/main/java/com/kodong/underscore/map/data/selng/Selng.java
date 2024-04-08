package com.kodong.underscore.map.data.selng;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.Selling;
import com.kodong.underscore.map.entity.ServiceIndustry;
import lombok.Data;

@Data
public class Selng {
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

    @JsonProperty("THSMON_SELNG_AMT")
    private long thsmonSelngAmt;

    @JsonProperty("THSMON_SELNG_CO")
    private long thsmonSelngCo;

    @JsonProperty("MDWK_SELNG_AMT")
    private long mdwkSelngAmt;

    @JsonProperty("WKEND_SELNG_AMT")
    private long wkendSelngAmt;

    @JsonProperty("MON_SELNG_AMT")
    private long monSelngAmt;

    @JsonProperty("TUES_SELNG_AMT")
    private long tuesSelngAmt;

    @JsonProperty("WED_SELNG_AMT")
    private long wedSelngAmt;

    @JsonProperty("THUR_SELNG_AMT")
    private long thurSelngAmt;

    @JsonProperty("FRI_SELNG_AMT")
    private long friSelngAmt;

    @JsonProperty("SAT_SELNG_AMT")
    private long satSelngAmt;

    @JsonProperty("SUN_SELNG_AMT")
    private long sunSelngAmt;

    @JsonProperty("TMZON_00_06_SELNG_AMT")
    private long tmzon0006SelngAmt;

    @JsonProperty("TMZON_06_11_SELNG_AMT")
    private long tmzon0611SelngAmt;

    @JsonProperty("TMZON_11_14_SELNG_AMT")
    private long tmzon1114SelngAmt;

    @JsonProperty("TMZON_14_17_SELNG_AMT")
    private long tmzon1417SelngAmt;

    @JsonProperty("TMZON_17_21_SELNG_AMT")
    private long tmzon1721SelngAmt;

    @JsonProperty("TMZON_21_24_SELNG_AMT")
    private long tmzon2124SelngAmt;

    @JsonProperty("ML_SELNG_AMT")
    private long mlSelngAmt;

    @JsonProperty("FML_SELNG_AMT")
    private long fmlSelngAmt;

    @JsonProperty("AGRDE_10_SELNG_AMT")
    private long agrde10SelngAmt;

    @JsonProperty("AGRDE_20_SELNG_AMT")
    private long agrde20SelngAmt;

    @JsonProperty("AGRDE_30_SELNG_AMT")
    private long agrde30SelngAmt;

    @JsonProperty("AGRDE_40_SELNG_AMT")
    private long agrde40SelngAmt;

    @JsonProperty("AGRDE_50_SELNG_AMT")
    private long agrde50SelngAmt;

    @JsonProperty("AGRDE_60_ABOVE_SELNG_AMT")
    private long agrde60AboveSelngAmt;

    @JsonProperty("MDWK_SELNG_CO")
    private long mdwkSelngCo;

    @JsonProperty("WKEND_SELNG_CO")
    private long wkendSelngCo;

    @JsonProperty("MON_SELNG_CO")
    private long monSelngCo;

    @JsonProperty("TUES_SELNG_CO")
    private long tuesSelngCo;

    @JsonProperty("WED_SELNG_CO")
    private long wedSelngCo;

    @JsonProperty("THUR_SELNG_CO")
    private long thurSelngCo;

    @JsonProperty("FRI_SELNG_CO")
    private long friSelngCo;

    @JsonProperty("SAT_SELNG_CO")
    private long satSelngCo;

    @JsonProperty("SUN_SELNG_CO")
    private long sunSelngCo;

    @JsonProperty("TMZON_00_06_SELNG_CO")
    private long tmzon0006SelngCo;

    @JsonProperty("TMZON_06_11_SELNG_CO")
    private long tmzon0611SelngCo;

    @JsonProperty("TMZON_11_14_SELNG_CO")
    private long tmzon1114SelngCo;

    @JsonProperty("TMZON_14_17_SELNG_CO")
    private long tmzon1417SelngCo;

    @JsonProperty("TMZON_17_21_SELNG_CO")
    private long tmzon1721SelngCo;

    @JsonProperty("TMZON_21_24_SELNG_CO")
    private long tmzon2124SelngCo;

    @JsonProperty("ML_SELNG_CO")
    private long mlSelngCo;

    @JsonProperty("FML_SELNG_CO")
    private long fmlSelngCo;

    @JsonProperty("AGRDE_10_SELNG_CO")
    private long agrde10SelngCo;

    @JsonProperty("AGRDE_20_SELNG_CO")
    private long agrde20SelngCo;

    @JsonProperty("AGRDE_30_SELNG_CO")
    private long agrde30SelngCo;

    @JsonProperty("AGRDE_40_SELNG_CO")
    private long agrde40SelngCo;

    @JsonProperty("AGRDE_50_SELNG_CO")
    private long agrde50SelngCo;

    @JsonProperty("AGRDE_60_ABOVE_SELNG_CO")
    private long agrde60AboveSelngCo;

    public Selling convertToSelling(AdministrativeDistrict dong, ServiceIndustry industry, Selng selng){
        return Selling.builder()
                .dong(dong)
                .serviceIndustry(industry)
                .selling(selng)
                .build();
    }

}
