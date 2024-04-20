package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.Store;
import lombok.Builder;
import lombok.Data;

@Data
public class BusinessAttractionReportStoreInfo {

    private int similarIndustryStoreCount;
    private int openingBusinessRate;
    private int openingBusinessStoreCount;
    private int closingBusinessRate;
    private int closingBusinessStoreCount;

    @Builder
    public BusinessAttractionReportStoreInfo(Store store){
        this.similarIndustryStoreCount = store.getSimilarIndustryStoreCount();
        this.openingBusinessRate = store.getOpeningBusinessRate();
        this.openingBusinessStoreCount = store.getOpeningBusinessStoreCount();
        this.closingBusinessRate = store.getClosingBusinessRate();
        this.closingBusinessStoreCount = store.getClosingBusinessStoreCount();
    }

}
