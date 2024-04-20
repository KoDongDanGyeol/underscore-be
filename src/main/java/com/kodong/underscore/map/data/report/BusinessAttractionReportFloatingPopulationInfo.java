package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.FloatingPopulation;
import lombok.Builder;
import lombok.Data;

@Data
public class BusinessAttractionReportFloatingPopulationInfo {
    private int totalFloatingPopulationCount;
    private int ageGrade10FloatingPopulationCount;
    private int ageGrade20FloatingPopulationCount;
    private int ageGrade30FloatingPopulationCount;
    private int ageGrade40FloatingPopulationCount;
    private int ageGrade50FloatingPopulationCount;
    private int ageGrade60AndAboveFloatingPopulationCount;
    private int mondayFloatingPopulationCount;
    private int tuesdayFloatingPopulationCount;
    private int wednesdayFloatingPopulationCount;
    private int thursdayFloatingPopulationCount;
    private int fridayFloatingPopulationCount;
    private int saturdayFloatingPopulationCount;
    private int sundayFloatingPopulationCount;

    @Builder
    public BusinessAttractionReportFloatingPopulationInfo (FloatingPopulation floatingPopulation) {
        this.totalFloatingPopulationCount = floatingPopulation.getTotFlpopCo();
        this.ageGrade10FloatingPopulationCount = floatingPopulation.getAgrde10FlpopCo();
        this.ageGrade20FloatingPopulationCount = floatingPopulation.getAgrde20FlpopCo();
        this.ageGrade30FloatingPopulationCount = floatingPopulation.getAgrde30FlpopCo();
        this.ageGrade40FloatingPopulationCount = floatingPopulation.getAgrde40FlpopCo();
        this.ageGrade50FloatingPopulationCount = floatingPopulation.getAgrde50FlpopCo();
        this.ageGrade60AndAboveFloatingPopulationCount = floatingPopulation.getAgrde60AboveFlpopCo();
        this.mondayFloatingPopulationCount = floatingPopulation.getMonFlpopCo();
        this.tuesdayFloatingPopulationCount = floatingPopulation.getTuesFlpopCo();
        this.wednesdayFloatingPopulationCount = floatingPopulation.getWedFlpopCo();
        this.thursdayFloatingPopulationCount = floatingPopulation.getThurFlpopCo();
        this.fridayFloatingPopulationCount = floatingPopulation.getFriFlpopCo();
        this.saturdayFloatingPopulationCount = floatingPopulation.getSatFlpopCo();
        this.sundayFloatingPopulationCount = floatingPopulation.getSunFlpopCo();
    }

}
