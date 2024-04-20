package com.kodong.underscore.map.data.report;

import com.kodong.underscore.map.entity.Selling;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BusinessAttractionReportSellingInfo {

    private long thisMonthSellingAmount;
    private long thisMonthSellingCount;
    private long midweekSellingCount;
    private long weekendSellingCount;
    private long mondaySellingCount;
    private long tuesdaySellingCount;
    private long wednesdaySellingCount;
    private long thursdaySellingCount;
    private long fridaySellingCount;
    private long saturdaySellingCount;
    private long sundaySellingCount;
    private long maleSellingCount;
    private long femaleSellingCount;
    private long ageGrade10SellingCount;
    private long ageGrade20SellingCount;
    private long ageGrade30SellingCount;
    private long ageGrade40SellingCount;
    private long ageGrade50SellingCount;
    private long ageGrade60AndAboveSellingCount;

    @Builder
    public BusinessAttractionReportSellingInfo(Selling selling){
        this.thisMonthSellingAmount = selling.getThsmonSelngAmt();
        this.thisMonthSellingCount = selling.getThsmonSelngCo();
        this.midweekSellingCount = selling.getMdwkSelngCo();
        this.weekendSellingCount = selling.getWkendSelngCo();
        this.mondaySellingCount = selling.getMonSelngCo();
        this.tuesdaySellingCount = selling.getTuesSelngCo();
        this.wednesdaySellingCount = selling.getWedSelngCo();
        this.thursdaySellingCount = selling.getThurSelngCo();
        this.fridaySellingCount = selling.getFriSelngCo();
        this.saturdaySellingCount = selling.getSatSelngCo();
        this.sundaySellingCount = selling.getSunSelngCo();
        this.maleSellingCount = selling.getMlSelngCo();
        this.femaleSellingCount = selling.getFmlSelngCo();
        this.ageGrade10SellingCount = selling.getAgrde10SelngCo();
        this.ageGrade20SellingCount = selling.getAgrde20SelngCo();
        this.ageGrade30SellingCount = selling.getAgrde30SelngCo();
        this.ageGrade40SellingCount = selling.getAgrde40SelngCo();
        this.ageGrade50SellingCount = selling.getAgrde50SelngCo();
        this.ageGrade60AndAboveSellingCount = selling.getAgrde60AboveSelngCo();
    }



}
