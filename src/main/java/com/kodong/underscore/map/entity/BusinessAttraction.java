package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.util.BusinessAttractionLabels;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder // Lombok의 @Builder 어노테이션 사용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusinessAttraction {

    @EmbeddedId
    private BusinessAttractionId id;

    private int floatingPopulationScore;
    private int incomeConsumptionScore;
    private int residentPopulationScore;
    private int indexQuarterlyQuotientScore;
    private int sellingScore;
    private int storeScore;
    private int totalScore;


    public BusinessAttraction(AdministrativeDistrict administrativeDistrict,
                              ServiceIndustry serviceIndustry, String standardYearQuarterCode) {
        this.id = BusinessAttractionId.builder()
                .administrativeDistrictId(administrativeDistrict)
                .serviceIndustryId(serviceIndustry)
                .standardYearQuarterCode(standardYearQuarterCode)
                .build();
        this.floatingPopulationScore = 0;
        this.incomeConsumptionScore = 0;
        this.residentPopulationScore = 0;
        this.indexQuarterlyQuotientScore = 0;
        this.sellingScore = 0;
        this.storeScore = 0;
        sumScores();
    }

    public void updateFloatingPopulationScore(int floatingPopulationScore) {
        this.floatingPopulationScore = floatingPopulationScore;
        sumScores();
    }

    public void updateIncomeConsumptionScore(int incomeConsumptionScore) {
        this.incomeConsumptionScore = incomeConsumptionScore;
        sumScores();
    }

    public void updateResidentPopulationScore(int residentPopulationScore) {
        this.residentPopulationScore = residentPopulationScore;
        sumScores();
    }

    public void updateStoreScore(int storeScore) {
        this.storeScore = storeScore;
        sumScores();
    }

    public void updateSellingScore(int sellingScore) {
        this.sellingScore = sellingScore;
        sumScores();
    }

    public void updateIndexQuarterlyQuotientScore(int quarterlyQuotientScore) {
        this.indexQuarterlyQuotientScore = quarterlyQuotientScore;
        sumScores();
    }

    private void sumScores(){
        this.totalScore =
                floatingPopulationScore + incomeConsumptionScore + residentPopulationScore
                + indexQuarterlyQuotientScore + sellingScore + storeScore;
    }
  
    public int[] getScoresForLoggedInUser() {
        BusinessAttractionLabels[] labels = BusinessAttractionLabels.values();
        int[] scores = new int[labels.length];  // Exclude 'totalScore'
        for (int i = 0; i < labels.length; i++) {
            scores[i] = BusinessAttractionLabels.getScore(this, labels[i]);
        }
        return scores;
    }

    public int[] getScoresForGuestUser() {
        BusinessAttractionLabels[] labels = BusinessAttractionLabels.values();
        int[] scores = new int[labels.length];  // Exclude 'totalScore'
        for (int i = 0; i < labels.length; i++) {
            if(i>1){
                break;
            }
            scores[i] = BusinessAttractionLabels.getScore(this, labels[i]);
        }
        return scores;
    }
}
