package com.kodong.underscore.map.util;

import com.kodong.underscore.map.entity.BusinessAttraction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessAttractionLabels {
    FLOATING_POPULATION("Floating Population"),
    STORES("Stores"),
    INCOME_CONSUMPTION("IncomeConsumption"),
    RESIDENT_POPULATION("ResidentPopulation"),
    INDEX_QUARTERLY_QUOTIENTS("IndexQuarterlyQuotients"),
    SELLING("Selling");

    private final String label;

    public static int getScore(BusinessAttraction businessAttraction, BusinessAttractionLabels label) {
        return switch (label) {
            case FLOATING_POPULATION -> businessAttraction.getFloatingPopulationScore();
            case STORES -> businessAttraction.getStoreScore();
            case INCOME_CONSUMPTION -> businessAttraction.getIncomeConsumptionScore();
            case RESIDENT_POPULATION -> businessAttraction.getResidentPopulationScore();
            case INDEX_QUARTERLY_QUOTIENTS -> businessAttraction.getIndexQuarterlyQuotientScore();
            case SELLING -> businessAttraction.getSellingScore();
            default -> throw new IllegalArgumentException("Unknown label: " + label);
        };
    }

    public static String[] getLabels() {
        BusinessAttractionLabels[] values = values();
        String[] labels = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            labels[i] = values[i].getLabel();
        }
        return labels;
    }
}
