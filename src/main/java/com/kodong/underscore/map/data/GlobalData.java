package com.kodong.underscore.map.data;

import com.kodong.underscore.map.entity.ServiceIndustry;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GlobalData {
    private String tokenForSGIS;
    private final List<ServiceIndustry> serviceIndustryList= new ArrayList<>();
    private final List<Integer> floatingPopulationThresholds = new ArrayList<>();
    private final List<Long>incomeConsumptionThresholds = new ArrayList<>();
    private final List<Integer> residentPopulationThresholds= new ArrayList<>();
    private final Map<String,List<Long>> sellingThresholds= new HashMap<>();
    private final Map<String,List<Integer>> storeThresholds= new HashMap<>();
    private final List<String> indexQuarterlyQuotientThresholds= new ArrayList<>(Arrays.asList("LL","HH","HL","LH"));
    private String standardYearQuarterCode = "20233";


    public List<ServiceIndustry> getServiceIndustryList() {
        return Collections.unmodifiableList(serviceIndustryList);
    }

    public List<Integer> getFloatingPopulationThresholds() {
        return Collections.unmodifiableList(floatingPopulationThresholds);
    }

    public List<Long> getIncomeConsumptionThresholds() {
        return Collections.unmodifiableList(incomeConsumptionThresholds);
    }

    public List<Integer> getResidentPopulationThresholds() {
        return Collections.unmodifiableList(residentPopulationThresholds);
    }

    public Map<String,List<Long>> getSellingThresholds() {
        return Collections.unmodifiableMap(sellingThresholds);
    }

    public Map<String,List<Integer>> getStoreThresholds() {
        return Collections.unmodifiableMap(storeThresholds);
    }

    public List<String> getIndexQuarterlyQuotientThresholds() {
        return Collections.unmodifiableList(indexQuarterlyQuotientThresholds);
    }

    public String getStandardYearQuarterCode() {
        return standardYearQuarterCode;
    }

    public void updateServiceIndustryList(List<ServiceIndustry> serviceIndustries) {
        this.serviceIndustryList.clear();
        this.serviceIndustryList.addAll(serviceIndustries);
    }

    public void updateFloatingPopulationThresholds(List<Integer> floatingPopulationThresholds) {
        this.floatingPopulationThresholds.clear();
        this.floatingPopulationThresholds.addAll(floatingPopulationThresholds);
    }

    public void updateIncomeConsumptionThresholds(List<Long> incomeConsumptionThresholds) {
        this.incomeConsumptionThresholds.clear();
        this.incomeConsumptionThresholds.addAll(incomeConsumptionThresholds);
    }

    public void updateResidentPopulationThresholds(List<Integer> residentPopulationThresholds) {
        this.residentPopulationThresholds.clear();
        this.residentPopulationThresholds.addAll(residentPopulationThresholds);
    }

    public void updateSellingThresholds(Map<String, List<Long>> sellingThresholds) {
        this.sellingThresholds.clear();
        this.sellingThresholds.putAll(sellingThresholds);
    }

    public void updateStoreThresholds(Map<String, List<Integer>> storeThresholds) {
        this.storeThresholds.clear();
        this.storeThresholds.putAll(storeThresholds);
    }

    public void updateIndexQuarterlyThresholds(List<String> indexQuarterlyQuotientThresholds) {
        this.indexQuarterlyQuotientThresholds.clear();
        this.indexQuarterlyQuotientThresholds.addAll(indexQuarterlyQuotientThresholds);
    }

    public void updateTokenForSGIS(String tokenForSGIS) {
        this.tokenForSGIS = tokenForSGIS;
    }

    public void updateStandardYearQuarterCode(String standardYearQuarterCode) {
        this.standardYearQuarterCode = standardYearQuarterCode;
    }

    public void printContent() {
        System.out.println("Token for SGIS: " + tokenForSGIS);

        System.out.println("Service Industry List:");
        serviceIndustryList.forEach(industry -> System.out.println(industry.toString()));

        System.out.println("Floating Population Thresholds:");
        floatingPopulationThresholds.forEach(System.out::println);

        System.out.println("\nIncome Consumption Thresholds:");
        incomeConsumptionThresholds.forEach(System.out::println);

        System.out.println("\nResident Population Thresholds:");
        residentPopulationThresholds.forEach(System.out::println);

        System.out.println("\nSelling Thresholds:");
        sellingThresholds.forEach((key, value) -> {
            System.out.println("Key: " + key);
            value.forEach(v -> System.out.println("  Value: " + v));
        });

        System.out.println("\nStore Thresholds:");
        storeThresholds.forEach((key, value) -> {
            System.out.println("Key: " + key);
            value.forEach(v -> System.out.println("  Value: " + v));
        });

        System.out.println("Index Quarterly Quotient Thresholds:");
        indexQuarterlyQuotientThresholds.forEach(System.out::println);
    }

}
