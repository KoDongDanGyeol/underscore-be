package com.kodong.underscore.map.data.repop;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.ResidentPopulation;
import lombok.Data;

@Data
public class Repop {
    @JsonProperty("STDR_YYQU_CD")
    private String standardYearQuarterCode;

    @JsonProperty("ADSTRD_CD")
    private String adstrdCode;

    @JsonProperty("ADSTRD_CD_NM")
    private String adstrdCodeName;

    @JsonProperty("TOT_REPOP_CO")
    private int totalRepopCount;

    @JsonProperty("ML_REPOP_CO")
    private int maleRepopCount;

    @JsonProperty("FML_REPOP_CO")
    private int femaleRepopCount;

    @JsonProperty("AGRDE_10_REPOP_CO")
    private int age10RepopCount;

    @JsonProperty("AGRDE_20_REPOP_CO")
    private int age20RepopCount;

    @JsonProperty("AGRDE_30_REPOP_CO")
    private int age30RepopCount;

    @JsonProperty("AGRDE_40_REPOP_CO")
    private int age40RepopCount;

    @JsonProperty("AGRDE_50_REPOP_CO")
    private int age50RepopCount;

    @JsonProperty("AGRDE_60_ABOVE_REPOP_CO")
    private int age60AboveRepopCount;

    @JsonProperty("MAG_10_REPOP_CO")
    private int maleAge10RepopCount;

    @JsonProperty("MAG_20_REPOP_CO")
    private int maleAge20RepopCount;

    @JsonProperty("MAG_30_REPOP_CO")
    private int maleAge30RepopCount;

    @JsonProperty("MAG_40_REPOP_CO")
    private int maleAge40RepopCount;

    @JsonProperty("MAG_50_REPOP_CO")
    private int maleAge50RepopCount;

    @JsonProperty("MAG_60_ABOVE_REPOP_CO")
    private int maleAge60AboveRepopCount;

    @JsonProperty("FAG_10_REPOP_CO")
    private int femaleAge10RepopCount;

    @JsonProperty("FAG_20_REPOP_CO")
    private int femaleAge20RepopCount;

    @JsonProperty("FAG_30_REPOP_CO")
    private int femaleAge30RepopCount;

    @JsonProperty("FAG_40_REPOP_CO")
    private int femaleAge40RepopCount;

    @JsonProperty("FAG_50_REPOP_CO")
    private int femaleAge50RepopCount;

    @JsonProperty("FAG_60_ABOVE_REPOP_CO")
    private int femaleAge60AboveRepopCount;

    @JsonProperty("TOT_HSHLD_CO")
    private int totalHouseholdCount;

    @JsonProperty("APT_HSHLD_CO")
    private int apartmentHouseholdCount;

    @JsonProperty("NON_APT_HSHLD_CO")
    private int nonApartmentHouseholdCount;


    public ResidentPopulation convertToResidentPopulation(AdministrativeDistrict dong, Repop repop){
        return ResidentPopulation.builder()
                .dong(dong)
                .repop(repop)
                .build();
    }
}
