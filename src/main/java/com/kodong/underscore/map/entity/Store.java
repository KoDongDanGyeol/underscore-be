package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.stor.Stor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    @ManyToOne
    @JoinColumn(name = "service_industry_id")
    private ServiceIndustry serviceIndustry;

    private String standardYearQuarterCode;
    private int storeCount;
    private int similarIndustryStoreCount;
    private int openingBusinessRate;
    private int openingBusinessStoreCount;
    private int closingBusinessRate;
    private int closingBusinessStoreCount;
    private int franchiseStoreCount;

    @Builder
    public Store(AdministrativeDistrict dong,ServiceIndustry serviceIndustry, Stor store){
        this.administrativeDistrict = dong;
        this.serviceIndustry = serviceIndustry;
        this.standardYearQuarterCode = store.getStandardYearQuarterCode();
        this.storeCount = store.getStoreCount();
        this.similarIndustryStoreCount = store.getSimilarIndustryStoreCount();
        this.openingBusinessRate = store.getOpeningBusinessRate();
        this.openingBusinessStoreCount = store.getOpeningBusinessStoreCount();
        this.closingBusinessRate = store.getClosingBusinessRate();
        this.closingBusinessStoreCount = store.getClosingBusinessStoreCount();
        this.franchiseStoreCount = store.getFranchiseStoreCount();
    }
}
