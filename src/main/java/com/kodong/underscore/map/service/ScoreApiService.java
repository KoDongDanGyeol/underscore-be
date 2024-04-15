package com.kodong.underscore.map.service;

import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.entity.*;
import com.kodong.underscore.map.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreApiService {
    private final ServiceIndustryRepository serviceIndustryRepository;
    private final GlobalData globalData;
    private final BusinessAttractionRepository businessAttractionRepository;
    private final AdministrativeDistrictRepository administrativeDistrictRepository;
    private final FloatingPopulationRepository floatingPopulationRepository;
    private final IncomeConsumptionRepository incomeConsumptionRepository;
    private final ResidentPopulationRepository residentPopulationRepository;
    private final SellingRepository sellingRepository;
    private final StoreRepository storeRepository;

    public Map<String, String> allServiceIndustryData(){
        Map<String,String> dtos = new LinkedHashMap<>();
        Sort sort = Sort.by(Sort.Direction.ASC, "serviceIndustryCode"); // 오름차순 정렬
        List<ServiceIndustry> datas = serviceIndustryRepository.findByServiceIndustryCodeContaining("CS100",sort);
        for(ServiceIndustry data : datas){
            dtos.put(data.getServiceIndustryCode(),data.getServiceIndustryCodeName());
        }
        return dtos;
    }

    public void insertServiceIndustryData(){
        putFoodDataIntoGlobalData();
    }

    private List<ServiceIndustry> putFoodDataIntoGlobalData() {
        Sort sort = Sort.by(Sort.Direction.ASC, "serviceIndustryCode"); // 오름차순 정렬
        List<ServiceIndustry> serviceIndustryList = serviceIndustryRepository.findByServiceIndustryCodeContaining("CS100",sort);
        globalData.updateServiceIndustryList(serviceIndustryList);
        return serviceIndustryList;
    }

    public void updateThresholds(){
        //List<ServiceIndustry> serviceIndustryList = serviceIndustryRepository.findAll();

        List<ServiceIndustry> serviceIndustryList = putFoodDataIntoGlobalData();


        updateFloatingPopulationThresholds();
        updateIncomeConsumptionThresholds();
        updateResidentPopulationThresholds();
        updateSellingThresholds(serviceIndustryList);
        updateStoreThresholds(serviceIndustryList);

        globalData.printContent();


    }

    private void updateStoreThresholds(List<ServiceIndustry> serviceIndustryList) {
        Map<String, List<Integer>> mapData = new HashMap<>();
        List<Store> data;
        int num = 0;
        List<Integer> thresholds;
        for(ServiceIndustry serviceIndustry : serviceIndustryList){
            data = storeRepository
                    .findAllByServiceIndustryAndStandardYearQuarterCodeOrderBySimilarIndustryStoreCount(serviceIndustry,globalData.getStandardYearQuarterCode());
            thresholds = new ArrayList<>();
            if(data.size() == 0){
                log.info("현재 이 Store Data는 비어 있습니다. (코드 : "+serviceIndustry.getServiceIndustryCode()+", 연분기 코드 : "+globalData.getStandardYearQuarterCode());
                continue;
            }
            for(int i = 1 ; i < 3 ; i++){
                num = data.get(i*(data.size()/4)).getSimilarIndustryStoreCount();
                thresholds.add(num);
            }

            mapData.put(serviceIndustry.getServiceIndustryCode(),thresholds);
        }
        globalData.updateStoreThresholds(mapData);
    }

    private void updateSellingThresholds(List<ServiceIndustry> serviceIndustryList) {
        Map<String, List<Long>> mapData = new HashMap<>();
        List<Selling> data;
        long num = 0;
        List<Long> thresholds;
        for(ServiceIndustry serviceIndustry : serviceIndustryList){
            data = sellingRepository
                    .findAllByServiceIndustryAndStandardYearQuarterCodeOrderByThsmonSelngAmt(serviceIndustry,globalData.getStandardYearQuarterCode());

            thresholds = new ArrayList<>();
            if(data.size() == 0){
                log.info("현재 이 selling Data는 비어 있습니다. (코드 : "+serviceIndustry.getServiceIndustryCode()+", 연분기 코드 : "+globalData.getStandardYearQuarterCode());
                continue;
            }
            for(int i = 1 ; i < 4 ; i++){
                num = data.get(i*(data.size()/4)).getThsmonSelngAmt();
                thresholds.add(num);
            }

            mapData.put(serviceIndustry.getServiceIndustryCode(),thresholds);
        }
        globalData.updateSellingThresholds(mapData);
    }

    private void updateResidentPopulationThresholds() {
        List<ResidentPopulation> data = residentPopulationRepository
                .findAllByStandardYearQuarterCodeOrderByTotalRepopCountAsc(globalData.getStandardYearQuarterCode());
        List<Integer> thresholds = new ArrayList<>();

        int num = 0;
        for(int i = 1 ; i < 4 ; i++){
            num = data.get(i*(data.size()/4)).getTotalRepopCount();
            thresholds.add(num);
        }

        globalData.updateResidentPopulationThresholds(thresholds);
    }

    private void updateIncomeConsumptionThresholds() {
        List<IncomeConsumption> data = incomeConsumptionRepository
                .findAllByStandardYearQuarterCodeOrderByFoodExpenditureAmountAsc(globalData.getStandardYearQuarterCode());
        List<Long> thresholds = new ArrayList<>();

        long num = 0;
        for(int i = 1 ; i < 3 ; i++){
            num = data.get(i*(data.size()/3)).getFoodExpenditureAmount();
            thresholds.add(num);
        }

        globalData.updateIncomeConsumptionThresholds(thresholds);
    }

    private void updateFloatingPopulationThresholds() {
        List<FloatingPopulation> data = floatingPopulationRepository
                .findAllByStandardYearQuarterCodeOrderByTotFlpopCoAsc(globalData.getStandardYearQuarterCode());
        List<Integer> thresholds = new ArrayList<>();

        int num = 0;
        for(int i = 1 ; i < 3 ; i++){
            num = data.get(i*(data.size()/3)).getTotFlpopCo();
            thresholds.add(num);
        }

        globalData.updateFloatingPopulationThresholds(thresholds);
    }

}
