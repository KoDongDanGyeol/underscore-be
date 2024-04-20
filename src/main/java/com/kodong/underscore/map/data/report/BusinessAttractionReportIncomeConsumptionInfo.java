package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.IncomeConsumption;
import lombok.Builder;
import lombok.Data;

@Data
public class BusinessAttractionReportIncomeConsumptionInfo {
    private long foodExpenditureAmount;

    @Builder
    public BusinessAttractionReportIncomeConsumptionInfo(IncomeConsumption incomeConsumption) {
        this.foodExpenditureAmount = incomeConsumption.getFoodExpenditureAmount();
    }
}
