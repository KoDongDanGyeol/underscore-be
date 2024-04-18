package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.repop.Repop;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResidentPopulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    private String standardYearQuarterCode;
    private int totalRepopCount;
    private int maleRepopCount;
    private int femaleRepopCount;
    private int age10RepopCount;
    private int age20RepopCount;
    private int age30RepopCount;
    private int age40RepopCount;
    private int age50RepopCount;
    private int age60AboveRepopCount;
    private int maleAge10RepopCount;
    private int maleAge20RepopCount;
    private int maleAge30RepopCount;
    private int maleAge40RepopCount;
    private int maleAge50RepopCount;
    private int maleAge60AboveRepopCount;
    private int femaleAge10RepopCount;
    private int femaleAge20RepopCount;
    private int femaleAge30RepopCount;
    private int femaleAge40RepopCount;
    private int femaleAge50RepopCount;
    private int femaleAge60AboveRepopCount;
    private int totalHouseholdCount;
    private int apartmentHouseholdCount;
    private int nonApartmentHouseholdCount;

    @Builder
    public ResidentPopulation (AdministrativeDistrict dong, Repop repop){
        this.administrativeDistrict = dong;
        this.standardYearQuarterCode = repop.getStandardYearQuarterCode();
        this.totalRepopCount = repop.getTotalRepopCount();
        this.maleRepopCount = repop.getMaleRepopCount();
        this.femaleRepopCount = repop.getFemaleRepopCount();
        this.age10RepopCount = repop.getAge10RepopCount();
        this.age20RepopCount = repop.getAge20RepopCount();
        this.age30RepopCount = repop.getAge30RepopCount();
        this.age40RepopCount = repop.getAge40RepopCount();
        this.age50RepopCount = repop.getAge50RepopCount();
        this.age60AboveRepopCount = repop.getAge60AboveRepopCount();
        this.maleAge10RepopCount = repop.getMaleAge10RepopCount();
        this.maleAge20RepopCount = repop.getMaleAge20RepopCount();
        this.maleAge30RepopCount = repop.getMaleAge30RepopCount();
        this.maleAge40RepopCount = repop.getMaleAge40RepopCount();
        this.maleAge50RepopCount = repop.getMaleAge50RepopCount();
        this.maleAge60AboveRepopCount = repop.getMaleAge60AboveRepopCount();
        this.femaleAge10RepopCount = repop.getFemaleAge10RepopCount();
        this.femaleAge20RepopCount = repop.getFemaleAge20RepopCount();
        this.femaleAge30RepopCount = repop.getFemaleAge30RepopCount();
        this.femaleAge40RepopCount = repop.getFemaleAge40RepopCount();
        this.femaleAge50RepopCount = repop.getFemaleAge50RepopCount();
        this.femaleAge60AboveRepopCount = repop.getFemaleAge60AboveRepopCount();
        this.totalHouseholdCount = repop.getTotalHouseholdCount();
        this.apartmentHouseholdCount = repop.getApartmentHouseholdCount();
        this.nonApartmentHouseholdCount = repop.getNonApartmentHouseholdCount();
    }

}
