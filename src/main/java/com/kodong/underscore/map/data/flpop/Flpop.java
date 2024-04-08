package com.kodong.underscore.map.data.flpop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.FloatingPopulation;
import lombok.Data;

@Data
public class Flpop {
    @JsonProperty("STDR_YYQU_CD")
    private String standardYearQuarterCode;

    @JsonProperty("ADSTRD_CD")
    private String adstrdCode;

    @JsonProperty("ADSTRD_CD_NM")
    private String adstrdCodeName;

    @JsonProperty("TOT_FLPOP_CO")
    private int totFlpopCo;

    @JsonProperty("ML_FLPOP_CO")
    private int mlFlpopCo;

    @JsonProperty("FML_FLPOP_CO")
    private int fmlFlpopCo;

    @JsonProperty("AGRDE_10_FLPOP_CO")
    private int agrde10FlpopCo;

    @JsonProperty("AGRDE_20_FLPOP_CO")
    private int agrde20FlpopCo;

    @JsonProperty("AGRDE_30_FLPOP_CO")
    private int agrde30FlpopCo;

    @JsonProperty("AGRDE_40_FLPOP_CO")
    private int agrde40FlpopCo;

    @JsonProperty("AGRDE_50_FLPOP_CO")
    private int agrde50FlpopCo;

    @JsonProperty("AGRDE_60_ABOVE_FLPOP_CO")
    private int agrde60AboveFlpopCo;

    @JsonProperty("TMZON_00_06_FLPOP_CO")
    private int tmzon0006FlpopCo;

    @JsonProperty("TMZON_06_11_FLPOP_CO")
    private int tmzon0611FlpopCo;

    @JsonProperty("TMZON_11_14_FLPOP_CO")
    private int tmzon1114FlpopCo;

    @JsonProperty("TMZON_14_17_FLPOP_CO")
    private int tmzon1417FlpopCo;

    @JsonProperty("TMZON_17_21_FLPOP_CO")
    private int tmzon1721FlpopCo;

    @JsonProperty("TMZON_21_24_FLPOP_CO")
    private int tmzon2124FlpopCo;

    @JsonProperty("MON_FLPOP_CO")
    private int monFlpopCo;

    @JsonProperty("TUES_FLPOP_CO")
    private int tuesFlpopCo;

    @JsonProperty("WED_FLPOP_CO")
    private int wedFlpopCo;

    @JsonProperty("THUR_FLPOP_CO")
    private int thurFlpopCo;

    @JsonProperty("FRI_FLPOP_CO")
    private int friFlpopCo;

    @JsonProperty("SAT_FLPOP_CO")
    private int satFlpopCo;

    @JsonProperty("SUN_FLPOP_CO")
    private int sunFlpopCo;

    public FloatingPopulation convertToFloatingPopulation(AdministrativeDistrict dong, Flpop flpop){
        return FloatingPopulation.builder()
                .dong(dong)
                .flpop(flpop)
                .build();
    }
}
