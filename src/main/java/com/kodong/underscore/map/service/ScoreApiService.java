package com.kodong.underscore.map.service;


import com.kodong.underscore.map.data.BusinessAttractionDTO;
import com.kodong.underscore.map.data.BusinessAttractionRequestData;
import com.kodong.underscore.map.data.BusinessAttractionResponseDTO;
import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.entity.*;
import com.kodong.underscore.map.repository.*;
import com.kodong.underscore.map.util.BusinessAttractionLabels;
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
    private final LegalDistrictRepository legalDistrictRepository;


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

    /**
     * globalData 클래스(메모리)에 식품 관련 서비스 업종분야만 추가하는 함수
     * @return 식품 관련 서비스 업종분야만 담긴 List
     */
    private List<ServiceIndustry> putFoodDataIntoGlobalData() {
        Sort sort = Sort.by(Sort.Direction.ASC, "serviceIndustryCode"); // 오름차순 정렬
        List<ServiceIndustry> serviceIndustryList = serviceIndustryRepository.findByServiceIndustryCodeContaining("CS100",sort);
        globalData.updateServiceIndustryList(serviceIndustryList);
        return serviceIndustryList;
    }

    /**
     * 모든 개업 매력도 점수 기준 수정하는 함수
     */
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

    /**
     * 인자로 넘어온 업종분야 별로 점포 기준 점수 수정
     * @param serviceIndustryList 업종분야
     */
    private void updateStoreThresholds(List<ServiceIndustry> serviceIndustryList) {
        Map<String, List<Integer>> mapData = new HashMap<>();
        List<Store> data;
        int num = 0;
        List<Integer> thresholds;
        for(ServiceIndustry serviceIndustry : serviceIndustryList){
            data = storeRepository
                    .findAllByServiceIndustryAndStandardYearQuarterCodeOrderBySimilarIndustryStoreCount(serviceIndustry,globalData.getStandardYearQuarterCode());
            thresholds = new ArrayList<>();
            
            if(data.isEmpty()){
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


    /**
     * 인자로 넘어온 업종분야 별로 매출 점수 기준 수정
     * @param serviceIndustryList 업종분야
     */
    private void updateSellingThresholds(List<ServiceIndustry> serviceIndustryList) {
        Map<String, List<Long>> mapData = new HashMap<>();
        List<Selling> data;
        long num = 0;
        List<Long> thresholds;
        for(ServiceIndustry serviceIndustry : serviceIndustryList){
            data = sellingRepository
                    .findAllByServiceIndustryAndStandardYearQuarterCodeOrderByThsmonSelngAmt(serviceIndustry,globalData.getStandardYearQuarterCode());

            thresholds = new ArrayList<>();

            if(data.isEmpty()){
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

    /**
     * 상주인구 점수 기준 수정
     */
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

    /**
     * 소득소비 점수 기준 수정
     */
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

    /**
     * 유동인구 점수 기준 수정
     */
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

    /**
     * 로그인한 사용자가 점수 요청할 경우 모든 점수 넘겨주는 메서드
     * 개업 매력도 점수가 담긴 ResponseDTO를 만드는 메서드
     * 총 개업 매력도 개수, 서비스 가능 지역 포함 여부 등이 포함됨
     * @param requestData
     * @return 개업매력도 점수 ResponseDTO
     */
    public BusinessAttractionResponseDTO getBusinessAttractionsForLoggedInUser(BusinessAttractionRequestData requestData) {
        putFoodDataIntoGlobalData();
        boolean containsInserviceableArea = checkContainsInserviceableArea(requestData.getLegalDistrictCode());
        checkTooMuchLegalDistrict(requestData.getLegalDistrictCode());
        String[] labels = BusinessAttractionLabels.getLabels();
        validateServiceIndustry(requestData.getServiceIndustryCode());
        String serviceIndustryCode = requestData.getServiceIndustryCode();

        ServiceIndustry serviceIndustry =serviceIndustryRepository.findByServiceIndustryCode(serviceIndustryCode).orElseThrow();
        List<String> legalDistrictCodesInSeoul = getServiceAreaLegalCodes(requestData.getLegalDistrictCode());

        List<BusinessAttractionDTO> businessAttractionDTOS = new ArrayList<>();
        BusinessAttractionDTO businessAttractionDTO;
        for(String legalDistrictCode : legalDistrictCodesInSeoul){
            businessAttractionDTO = makeBusinessAttractionDTOForLoggedInUser(serviceIndustry, legalDistrictCode);
            businessAttractionDTOS.add(businessAttractionDTO);
        }

        return BusinessAttractionResponseDTO.builder()
                .count(businessAttractionDTOS.size())
                .includesUnserviceableAreas(containsInserviceableArea)
                .labels(labels)
                .businessAttractions(businessAttractionDTOS)
                .build();
    }

    /**
     * 업종분야 코드와 법정동 코드를 받아서 적절하게 처리후 개업 매력도 DTO 생성
     * @param serviceIndustry
     * @param legalDistrictCode
     * @return 개업 매력도 DTO
     */
    private BusinessAttractionDTO makeBusinessAttractionDTOForLoggedInUser(ServiceIndustry serviceIndustry, String legalDistrictCode) {

        String administrativeCode = legalDistrictRepository.findAdministrativeCodeByLegalDistrictCode(legalDistrictCode).orElseThrow();
        AdministrativeDistrict administrativeDistrict = administrativeDistrictRepository.findByAdministrativeCode(administrativeCode).orElseThrow();

        BusinessAttractionId id = BusinessAttractionId.builder()
                .serviceIndustryId(serviceIndustry)
                .administrativeDistrictId(administrativeDistrict)
                .standardYearQuarterCode(globalData.getStandardYearQuarterCode())
                .build();

        BusinessAttraction attraction = businessAttractionRepository.findById(id).orElseThrow();

        return BusinessAttractionDTO.builder()
                .legalDistrictCode(legalDistrictCode)
                .administrativeDistrictName(administrativeDistrict.getEupMyeonDong())
                .businessAttractionScores(attraction.getScoresForLoggedInUser())
                .totalScore(attraction.getTotalScore())
                .build();
    }

    /**
     * 로그인하지 않은 게스트 사용자가 점수 요청할 경우 모든 점수 넘겨주는 메서드
     * 개업 매력도 점수가 담긴 ResponseDTO를 만드는 메서드
     * 총 개업 매력도 개수, 서비스 가능 지역 포함 여부 등이 포함됨
     * @param requestData
     * @return 개업매력도 점수 ResponseDTO
     */
    public BusinessAttractionResponseDTO getBusinessAttractionsForGuestUser(BusinessAttractionRequestData requestData) {
        putFoodDataIntoGlobalData();
        boolean containsInserviceableArea = checkContainsInserviceableArea(requestData.getLegalDistrictCode());
        checkTooMuchLegalDistrict(requestData.getLegalDistrictCode());
        String[] labels = BusinessAttractionLabels.getLabels();
        validateServiceIndustry(requestData.getServiceIndustryCode());
        String serviceIndustryCode = requestData.getServiceIndustryCode();

        ServiceIndustry serviceIndustry =serviceIndustryRepository.findByServiceIndustryCode(serviceIndustryCode).orElseThrow();
        List<String> legalDistrictCodesInSeoul = getServiceAreaLegalCodes(requestData.getLegalDistrictCode());

        List<BusinessAttractionDTO> businessAttractionDTOS = new ArrayList<>();
        BusinessAttractionDTO businessAttractionDTO;
        for(String legalDistrictCode : legalDistrictCodesInSeoul){
            businessAttractionDTO = makeBusinessAttractionDTOForGuestUser(serviceIndustry, legalDistrictCode);
            businessAttractionDTOS.add(businessAttractionDTO);
        }

        return BusinessAttractionResponseDTO.builder()
                .count(businessAttractionDTOS.size())
                .includesUnserviceableAreas(containsInserviceableArea)
                .labels(labels)
                .businessAttractions(businessAttractionDTOS)
                .build();

    }

    /**
     * 20개 이상의 개업 매력도 표시해야 하면 에러처리
     * @param legalDistrictCode
     */
    private void checkTooMuchLegalDistrict(List<String> legalDistrictCode) {
        if(legalDistrictCode.size() > 20){
            // TODO 20개가 넘는 개업 매력도를 표현해야 하는 경우 에러 발생
        }
    }

    /**
     * 업종분야 코드와 법정동 코드를 받아서 적절하게 처리후 개업 매력도 DTO 생성
     * @param serviceIndustry
     * @param legalDistrictCode
     * @return 개업 매력도 DTO
     */
    private BusinessAttractionDTO makeBusinessAttractionDTOForGuestUser(ServiceIndustry serviceIndustry, String legalDistrictCode) {

        String administrativeCode = legalDistrictRepository.findAdministrativeCodeByLegalDistrictCode(legalDistrictCode).orElseThrow();
        AdministrativeDistrict administrativeDistrict = administrativeDistrictRepository.findByAdministrativeCode(administrativeCode).orElseThrow();

        BusinessAttractionId id = BusinessAttractionId.builder()
                .serviceIndustryId(serviceIndustry)
                .administrativeDistrictId(administrativeDistrict)
                .standardYearQuarterCode(globalData.getStandardYearQuarterCode())
                .build();

        BusinessAttraction attraction = businessAttractionRepository.findById(id).orElseThrow();
        int[] scores = attraction.getScoresForGuestUser();
        int sum = Arrays.stream(scores).sum();

        return BusinessAttractionDTO.builder()
                .legalDistrictCode(legalDistrictCode)
                .administrativeDistrictName(administrativeDistrict.getEupMyeonDong())
                .businessAttractionScores(scores)
                .totalScore(sum)
                .build();
    }

    /**
     * 업종 분야 코드가 정상인지 확인하는 부분
     * 빈 문자열이 넘어오면 에러 처리
     * DB에 없는 문자열이 넘어올 경우 에러처리
     * @param serviceIndustryCode
     */
    private void validateServiceIndustry(String serviceIndustryCode) {
        if(serviceIndustryCode == null || serviceIndustryCode.isEmpty()){
            //TODO 업종분야 코드가 제대로 안넘어온 경우 에러처리
        }

        List<ServiceIndustry> availableIndustryCodes = globalData.getServiceIndustryList();
        boolean hasMatchingServiceIndustry = false;
        for(ServiceIndustry serviceIndustry : availableIndustryCodes){
            if(serviceIndustryCode.equals(serviceIndustry.getServiceIndustryCode())){
                hasMatchingServiceIndustry = true;
                break;
            }
        }
        if(!hasMatchingServiceIndustry){
            // TODO 업종분야 코드가 매칭되는게 없는 경우 에러처리
        }
    }

    /**
     * 서비스 제공 지역의 법정동 코드만 남기도록 제거
     * 이건 굳이 HashSet이 아니라 List로 해도 좋을 듯
     * @param legalDistrictCode
     * @return 서비스 제공 지역의 법정동 코드가 담긴 Set
     */
    private List<String> getServiceAreaLegalCodes(List<String> legalDistrictCode) {
        List<String> legalDistrictCodes = new ArrayList<>();
        for(String code : legalDistrictCode){
            if(!code.startsWith("11")){
                continue;
            }
            legalDistrictCodes.add(code);
        }

        return legalDistrictCodes;
    }

    /**
     * 서비스 불가 지역을 포함하고 있는지 확인하는 함수
     * @param legalDistrictCode
     * @return 서비스 불가 지역 포함여부
     */
    private boolean checkContainsInserviceableArea(List<String> legalDistrictCode) {
        for(String code : legalDistrictCode){
            if(!code.startsWith("11")){
                return true;
            }
        }
        return false;
    }
}
