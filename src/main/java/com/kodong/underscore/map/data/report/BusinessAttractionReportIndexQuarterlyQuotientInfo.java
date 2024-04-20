package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.IndexQuarterlyQuotient;
import lombok.Builder;
import lombok.Data;

@Data
public class BusinessAttractionReportIndexQuarterlyQuotientInfo {

    private String tradeAreaChangeIndex;
    private String tradeAreaChangeIndexName;
    private int operatingBusinessMonthAverage;
    private int closedBusinessMonthAverage;
    private int seoulOperatingBusinessMonthAverage;
    private int seoulClosedBusinessMonthAverage;

    @Builder
    public BusinessAttractionReportIndexQuarterlyQuotientInfo(IndexQuarterlyQuotient indexQuarterlyQuotient) {
        this.tradeAreaChangeIndex = indexQuarterlyQuotient.getTrdarChngeIx();
        this.tradeAreaChangeIndexName = indexQuarterlyQuotient.getTrdarChngeIxNm();
        this.operatingBusinessMonthAverage = indexQuarterlyQuotient.getOprSaleMtAvrg();
        this.closedBusinessMonthAverage = indexQuarterlyQuotient.getClsSaleMtAvrg();
        this.seoulOperatingBusinessMonthAverage = indexQuarterlyQuotient.getSuOprSaleMtAvrg();
        this.seoulClosedBusinessMonthAverage = indexQuarterlyQuotient.getSuClsSaleMtAvrg();
    }
}
