package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.ncmcnsmp.NcmCnsmp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeConsumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    private String standardYearQuarterCode;
    private long monthlyAverageIncomeAmount;
    private String incomeSectionCode;
    private long totalExpenditureAmount;
    private long foodStaffsExpenditureAmount;
    private long clothesFootwearExpenditureAmount;
    private long leisureSpecialExpenditureAmount;
    private long medicalCarePharmacyExpenditureAmount;
    private long transportExpenditureAmount;
    private long educationExpenditureAmount;
    private long pleasureExpenditureAmount;
    private long leisureCultureExpenditureAmount;
    private long etcExpenditureAmount;
    private long foodExpenditureAmount;

    @Builder
    public IncomeConsumption (AdministrativeDistrict dong, NcmCnsmp ncmCnsmp){
        this.administrativeDistrict = dong;
        this.standardYearQuarterCode = ncmCnsmp.getStandardYearQuarterCode();
        this.monthlyAverageIncomeAmount = ncmCnsmp.getMonthlyAverageIncomeAmount();
        this.incomeSectionCode = ncmCnsmp.getIncomeSectionCode();
        this.totalExpenditureAmount = ncmCnsmp.getTotalExpenditureAmount();
        this.foodStaffsExpenditureAmount = ncmCnsmp.getFoodStaffsExpenditureAmount();
        this.clothesFootwearExpenditureAmount = ncmCnsmp.getClothesFootwearExpenditureAmount();
        this.leisureSpecialExpenditureAmount = ncmCnsmp.getLeisureSpecialExpenditureAmount();
        this.medicalCarePharmacyExpenditureAmount = ncmCnsmp.getMedicalCarePharmacyExpenditureAmount();
        this.transportExpenditureAmount = ncmCnsmp.getTransportExpenditureAmount();
        this.educationExpenditureAmount = ncmCnsmp.getEducationExpenditureAmount();
        this.pleasureExpenditureAmount = ncmCnsmp.getPleasureExpenditureAmount();
        this.leisureCultureExpenditureAmount = ncmCnsmp.getLeisureCultureExpenditureAmount();
        this.etcExpenditureAmount = ncmCnsmp.getEtcExpenditureAmount();
        this.foodExpenditureAmount = ncmCnsmp.getFoodExpenditureAmount();

    }
}
