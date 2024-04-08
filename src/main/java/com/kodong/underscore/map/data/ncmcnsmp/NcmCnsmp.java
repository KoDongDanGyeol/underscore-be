package com.kodong.underscore.map.data.ncmcnsmp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.IncomeConsumption;
import lombok.Data;

@Data
public class NcmCnsmp {
    @JsonProperty("STDR_YYQU_CD")
    private String standardYearQuarterCode;

    @JsonProperty("ADSTRD_CD")
    private String adstrdCode;

    @JsonProperty("ADSTRD_CD_NM")
    private String adstrdCodeName;

    @JsonProperty("MT_AVRG_INCOME_AMT")
    private long monthlyAverageIncomeAmount;

    @JsonProperty("INCOME_SCTN_CD")
    private String incomeSectionCode;

    @JsonProperty("EXPNDTR_TOTAMT")
    private long totalExpenditureAmount;

    @JsonProperty("FDSTFFS_EXPNDTR_TOTAMT")
    private long foodStaffsExpenditureAmount;

    @JsonProperty("CLTHS_FTWR_EXPNDTR_TOTAMT")
    private long clothesFootwearExpenditureAmount;

    @JsonProperty("LVSPL_EXPNDTR_TOTAMT")
    private long leisureSpecialExpenditureAmount;

    @JsonProperty("MCP_EXPNDTR_TOTAMT")
    private long medicalCarePharmacyExpenditureAmount;

    @JsonProperty("TRNSPORT_EXPNDTR_TOTAMT")
    private long transportExpenditureAmount;

    @JsonProperty("EDC_EXPNDTR_TOTAMT")
    private long educationExpenditureAmount;

    @JsonProperty("PLESR_EXPNDTR_TOTAMT")
    private long pleasureExpenditureAmount;

    @JsonProperty("LSR_CLTUR_EXPNDTR_TOTAMT")
    private long leisureCultureExpenditureAmount;

    @JsonProperty("ETC_EXPNDTR_TOTAMT")
    private long etcExpenditureAmount;

    @JsonProperty("FD_EXPNDTR_TOTAMT")
    private long foodExpenditureAmount;

    public IncomeConsumption convertToIncomeConsumption(AdministrativeDistrict dong, NcmCnsmp ncmCnsmp){
        return IncomeConsumption.builder()
                .dong(dong)
                .ncmCnsmp(ncmCnsmp)
                .build();
    }
}
