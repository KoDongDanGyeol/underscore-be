package com.kodong.underscore.map.service;


import com.kodong.underscore.map.data.*;
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

    public void putAllServiceIndustryDataInGlobalData(){
        globalData.updateServiceIndustryList(serviceIndustryRepository.findAll());
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
        List<AdministrativeDistrict> administrativeDistrictsInRange = getAdministrativeDistrictInRange(requestData);
        checkTooMuchAdministrativeDistrict(administrativeDistrictsInRange);
        boolean containsInserviceableArea = checkContainsInserviceableArea(administrativeDistrictsInRange);
        String[] labels = BusinessAttractionLabels.getLabels();
        String serviceIndustryCode = validateServiceIndustry(requestData.getServiceIndustryCode());

        ServiceIndustry serviceIndustry =serviceIndustryRepository.findByServiceIndustryCode(serviceIndustryCode).orElseThrow();
        List<AdministrativeDistrict> administrativeDistrictsInSeoul = getAdministrativeDistrictsInServiceArea(administrativeDistrictsInRange);

        List<BusinessAttractionDTO> businessAttractionDTOS = new ArrayList<>();
        BusinessAttractionDTO businessAttractionDTO;
        for(AdministrativeDistrict district : administrativeDistrictsInSeoul){
            businessAttractionDTO = makeBusinessAttractionDTOForLoggedInUser(serviceIndustry, district);
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
     * 업종분야 객체와 행정동 객체를 받아서 적절하게 처리후 개업 매력도 DTO 생성
     * 전체 개업 매력도 점수를 모두 다 포함
     * @param serviceIndustry 업종분야 객체
     * @param administrativeDistrict 행정동 객체
     * @return 개업매력도 DTO
     */
    private BusinessAttractionDTO makeBusinessAttractionDTOForLoggedInUser(ServiceIndustry serviceIndustry, AdministrativeDistrict administrativeDistrict) {

        BusinessAttractionId id = BusinessAttractionId.builder()
                .serviceIndustryId(serviceIndustry)
                .administrativeDistrictId(administrativeDistrict)
                .standardYearQuarterCode(globalData.getStandardYearQuarterCode())
                .build();

        BusinessAttraction attraction = businessAttractionRepository.findById(id).orElseThrow();

        return BusinessAttractionDTO.builder()
                .administrativeDistrictName(administrativeDistrict.getFullAddress())
                .businessAttractionScores(attraction.getScoresForLoggedInUser())
                .totalScore(attraction.getTotalScore())
                .xLongitude(administrativeDistrict.getXLongitude())
                .yLatitude(administrativeDistrict.getYLatitude())
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
        List<AdministrativeDistrict> administrativeDistrictsInRange = getAdministrativeDistrictInRange(requestData);
        checkTooMuchAdministrativeDistrict(administrativeDistrictsInRange);
        boolean containsInserviceableArea = checkContainsInserviceableArea(administrativeDistrictsInRange);
        String[] labels = BusinessAttractionLabels.getLabels();
        String serviceIndustryCode = validateServiceIndustry(requestData.getServiceIndustryCode());


        ServiceIndustry serviceIndustry =serviceIndustryRepository.findByServiceIndustryCode(serviceIndustryCode).orElseThrow();
        List<AdministrativeDistrict> administrativeDistrictsInSeoul = getAdministrativeDistrictsInServiceArea(administrativeDistrictsInRange);

        List<BusinessAttractionDTO> businessAttractionDTOS = new ArrayList<>();
        BusinessAttractionDTO businessAttractionDTO;
        for(AdministrativeDistrict district : administrativeDistrictsInSeoul){
            businessAttractionDTO = makeBusinessAttractionDTOForGuestUser(serviceIndustry, district);
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
     * @param districts
     */
    private void checkTooMuchAdministrativeDistrict(List<AdministrativeDistrict> districts) {
        if(districts.size() > 20){
            // TODO 20개가 넘는 개업 매력도를 표현해야 하는 경우 에러 발생
        }
    }

    /**
     * 업종분야 객체와 행정동 객체를 받아서 적절하게 처리후 개업 매력도 DTO 생성
     * 일부 매력도 점수를 모두 다 포함
     * @param serviceIndustry 업종분야 객체
     * @param administrativeDistrict 행정동 객체
     * @return 개업매력도 DTO
     */
    private BusinessAttractionDTO makeBusinessAttractionDTOForGuestUser(ServiceIndustry serviceIndustry, AdministrativeDistrict administrativeDistrict) {

        BusinessAttractionId id = BusinessAttractionId.builder()
                .serviceIndustryId(serviceIndustry)
                .administrativeDistrictId(administrativeDistrict)
                .standardYearQuarterCode(globalData.getStandardYearQuarterCode())
                .build();

        BusinessAttraction attraction = businessAttractionRepository.findById(id).orElseThrow();
        int[] scores = attraction.getScoresForGuestUser();
        int sum = Arrays.stream(scores).sum();

        return BusinessAttractionDTO.builder()
                .administrativeDistrictName(administrativeDistrict.getFullAddress())
                .businessAttractionScores(scores)
                .totalScore(sum)
                .xLongitude(administrativeDistrict.getXLongitude())
                .yLatitude(administrativeDistrict.getYLatitude())
                .build();
    }

    /**
     * 업종 분야 코드가 정상인지 확인하는 부분
     * 빈 문자열이 넘어오면 에러 처리
     * DB에 없는 문자열이 넘어올 경우 에러처리
     * @param serviceIndustryCode
     */
    private String validateServiceIndustry(String serviceIndustryCode) {
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

        return serviceIndustryCode;
    }


    private List<AdministrativeDistrict> getAdministrativeDistrictsInServiceArea(List<AdministrativeDistrict> districts) {
        List<AdministrativeDistrict> districtsInServiceArea = new ArrayList<>();
        for(AdministrativeDistrict district : districts){
            if(!district.getAdministrativeCode().startsWith("11")){
                continue;
            }
            districtsInServiceArea.add(district);
        }

        return districtsInServiceArea;
    }

    /**
     * 서비스 불가 지역을 포함하고 있는지 확인하는 함수
     * @param districts 필요 범위 내의 행정동들이 담기 리스트
     * @return 서비스 불가 지역 포함여부
     */
    private boolean checkContainsInserviceableArea(List<AdministrativeDistrict> districts) {
        for(AdministrativeDistrict district : districts){
            if(!district.getAdministrativeCode().startsWith("11")){
                return true;
            }
        }
        return false;
    }

    /**
     * 클라이언트로부터 지도의 범위를 받았을 때 그 범위 내에 포함된 행정동 list 반환
     * @param requestData 지도의 범위가 담긴 DTO
     * @return 범위 내에 포함된 행정동 List
     */
    private List<AdministrativeDistrict> getAdministrativeDistrictInRange(BusinessAttractionRequestData requestData) {
        return administrativeDistrictRepository.findByXLongitudeBetweenAndYLatitudeBetween(
                requestData.getMinXLongitude(),
                requestData.getMaxXLongitude(),
                requestData.getMinYLatitude(),
                requestData.getMaxYLatitude()
        );
    }
}
