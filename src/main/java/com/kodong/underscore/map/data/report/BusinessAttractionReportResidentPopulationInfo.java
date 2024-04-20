package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.ResidentPopulation;
import lombok.Builder;
import lombok.Data;

@Data
public class BusinessAttractionReportResidentPopulationInfo {

    private int totalResidentPopulationCount;
    private int maleAge10ResidentPopulationCount;
    private int maleAge20ResidentPopulationCount;
    private int maleAge30ResidentPopulationCount;
    private int maleAge40ResidentPopulationCount;
    private int maleAge50ResidentPopulationCount;
    private int maleAge60AndAboveResidentPopulationCount;
    private int femaleAge10ResidentPopulationCount;
    private int femaleAge20ResidentPopulationCount;
    private int femaleAge30ResidentPopulationCount;
    private int femaleAge40ResidentPopulationCount;
    private int femaleAge50ResidentPopulationCount;
    private int femaleAge60AndAboveResidentPopulationCount;

    @Builder
    public BusinessAttractionReportResidentPopulationInfo(ResidentPopulation residentPopulation){
        this.totalResidentPopulationCount = residentPopulation.getTotalRepopCount();
        this.maleAge10ResidentPopulationCount = residentPopulation.getMaleAge10RepopCount();
        this.maleAge20ResidentPopulationCount = residentPopulation.getMaleAge20RepopCount();
        this.maleAge30ResidentPopulationCount = residentPopulation.getMaleAge30RepopCount();
        this.maleAge40ResidentPopulationCount = residentPopulation.getMaleAge40RepopCount();
        this.maleAge50ResidentPopulationCount = residentPopulation.getMaleAge50RepopCount();
        this.maleAge60AndAboveResidentPopulationCount = residentPopulation.getMaleAge60AboveRepopCount();
        this.femaleAge10ResidentPopulationCount = residentPopulation.getFemaleAge10RepopCount();
        this.femaleAge20ResidentPopulationCount = residentPopulation.getFemaleAge20RepopCount();
        this.femaleAge30ResidentPopulationCount = residentPopulation.getFemaleAge30RepopCount();
        this.femaleAge40ResidentPopulationCount = residentPopulation.getFemaleAge40RepopCount();
        this.femaleAge50ResidentPopulationCount = residentPopulation.getFemaleAge50RepopCount();
        this.femaleAge60AndAboveResidentPopulationCount = residentPopulation.getFemaleAge60AboveRepopCount();
    }

}
