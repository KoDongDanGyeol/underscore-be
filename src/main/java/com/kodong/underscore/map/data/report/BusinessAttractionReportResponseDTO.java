package com.kodong.underscore.map.data.report;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BusinessAttractionReportResponseDTO {

    // 에러 메세지
    private String errorMessage;

    // 행정동의 id
    private Long id;

    // 행정동의 전체 주소
    private String address;

    // 유동인구 정보
    private BusinessAttractionReportFloatingPopulationInfo floatingPopulationInfo;

    // 소득소비 정보
    private BusinessAttractionReportIncomeConsumptionInfo incomeConsumptionInfo;

    // 상권변화 지표
    private BusinessAttractionReportIndexQuarterlyQuotientInfo indexQuarterlyQuotientInfo;

    // 상주인구
    private BusinessAttractionReportResidentPopulationInfo  reportResidentPopulationInfo;

    // 매출
    private BusinessAttractionReportSellingInfo sellingInfo;

    // 점포 수
    private BusinessAttractionReportStoreInfo storeInfo;

    public BusinessAttractionReportSellingInfo updateSellingInfo(BusinessAttractionReportSellingInfo sellingInfo) {
        this.sellingInfo = sellingInfo;
        return sellingInfo;
    }

    public BusinessAttractionReportStoreInfo updateStoreInfo(BusinessAttractionReportStoreInfo storeInfo) {
        this.storeInfo = storeInfo;
        return storeInfo;
    }

    public String updateErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return errorMessage;
    }




}
