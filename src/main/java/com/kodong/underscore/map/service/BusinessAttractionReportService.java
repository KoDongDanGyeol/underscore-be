package com.kodong.underscore.map.service;

import com.kodong.underscore.map.data.BusinessAttractionRequestData;
import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.data.report.*;
import com.kodong.underscore.map.entity.*;
import com.kodong.underscore.map.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessAttractionReportService {

    private final AdministrativeDistrictRepository administrativeDistrictRepository;
    private final ServiceIndustryRepository serviceIndustryRepository;
    private final StoreRepository storeRepository;
    private final GlobalData globalData;
    private final SellingRepository sellingRepository;
    private final ResidentPopulationRepository residentPopulationRepository;
    private final IndexQuarterlyQuotientRepository indexQuarterlyQuotientRepository;
    private final IncomeConsumptionRepository incomeConsumptionRepository;
    private final FloatingPopulationRepository floatingPopulationRepository;

    public BusinessAttractionReportResponseDTO getReport(BusinessAttractionRequestData reportData){
        AdministrativeDistrict district = administrativeDistrictRepository.findById(reportData.getAdministrativeDistrictId()).orElseThrow();
        ServiceIndustry serviceIndustry = serviceIndustryRepository.findByServiceIndustryCode(reportData.getServiceIndustryCode()).orElseThrow();

        BusinessAttractionReportFloatingPopulationInfo floatingPopulationInfo = getFloatingPopulationInfo(district);
        BusinessAttractionReportIncomeConsumptionInfo incomeConsumptionInfo = getIncomeConsumptionInfo(district);
        BusinessAttractionReportIndexQuarterlyQuotientInfo indexQuarterlyQuotientInfo = getIndexQuarterlyQuotientInfo(district);
        BusinessAttractionReportResidentPopulationInfo residentPopulationInfo = getResidentPopulationInfo(district);

        BusinessAttractionReportResponseDTO responseDTO = BusinessAttractionReportResponseDTO.builder()
                .id(district.getId())
                .address(district.getFullAddress())
                .floatingPopulationInfo(floatingPopulationInfo)
                .incomeConsumptionInfo(incomeConsumptionInfo)
                .indexQuarterlyQuotientInfo(indexQuarterlyQuotientInfo)
                .reportResidentPopulationInfo(residentPopulationInfo)
                .build();

        return checkDistrictContainsServiceAndUpdateResponseData(responseDTO, district, serviceIndustry);



    }

    private BusinessAttractionReportResponseDTO checkDistrictContainsServiceAndUpdateResponseData(BusinessAttractionReportResponseDTO responseDTO, AdministrativeDistrict district, ServiceIndustry serviceIndustry) {
        Optional<Store> storeData = storeRepository.findByStandardYearQuarterCodeAndAdministrativeDistrictAndServiceIndustry(
                globalData.getStandardYearQuarterCode(), district, serviceIndustry
        );
        Optional<Selling> sellingData = sellingRepository.findByStandardYearQuarterCodeAndAdministrativeDistrictAndServiceIndustry(
                globalData.getStandardYearQuarterCode(), district, serviceIndustry
        );
        BusinessAttractionReportStoreInfo storeInfo;
        BusinessAttractionReportSellingInfo sellingInfo;

        if (storeData.isPresent() && sellingData.isPresent()) {
            storeInfo = BusinessAttractionReportStoreInfo.builder().store(storeData.get()).build();
            sellingInfo = BusinessAttractionReportSellingInfo.builder().selling(sellingData.get()).build();
            responseDTO.updateSellingInfo(sellingInfo);
            responseDTO.updateStoreInfo(storeInfo);
            return responseDTO;
        }

        if (sellingData.isPresent()) {
            sellingInfo = BusinessAttractionReportSellingInfo.builder().selling(sellingData.get()).build();
            responseDTO.updateSellingInfo(sellingInfo);
            responseDTO.updateErrorMessage("현재 이 지역의 해당 서비스 업종 관련 점포 데이터가 존재하지 않습니다.");
            return responseDTO;
        }

        if (storeData.isPresent()) {
            storeInfo = BusinessAttractionReportStoreInfo.builder().store(storeData.get()).build();
            responseDTO.updateStoreInfo(storeInfo);
            responseDTO.updateErrorMessage("현재 이 지역의 해당 서비스 업종 관련 매출 데이터가 존재하지 않습니다.");
            return responseDTO;
        }

        responseDTO.updateErrorMessage("현재 이 지역의 해당 서비스 업종 관련 매출, 점포 데이터가 존재하지 않습니다.");
        return responseDTO;
    }

    private BusinessAttractionReportStoreInfo getStoreInfo(AdministrativeDistrict district, ServiceIndustry serviceIndustry) {
        Store storeData = storeRepository.findByStandardYearQuarterCodeAndAdministrativeDistrictAndServiceIndustry(
                globalData.getStandardYearQuarterCode(),
                district,
                serviceIndustry
        ).orElseThrow();

        return BusinessAttractionReportStoreInfo.builder().store(storeData).build();
    }

    private BusinessAttractionReportSellingInfo getSellingInfo(AdministrativeDistrict district, ServiceIndustry serviceIndustry) {
        Selling sellingData = sellingRepository.findByStandardYearQuarterCodeAndAdministrativeDistrictAndServiceIndustry(
                globalData.getStandardYearQuarterCode(),
                district,
                serviceIndustry
        ).orElseThrow();

        return BusinessAttractionReportSellingInfo.builder().selling(sellingData).build();

    }

    private BusinessAttractionReportResidentPopulationInfo getResidentPopulationInfo(AdministrativeDistrict district) {
        ResidentPopulation residentPopulationData = residentPopulationRepository.findByStandardYearQuarterCodeAndAdministrativeDistrict(
                globalData.getStandardYearQuarterCode(), district
        ).orElseThrow();

        return BusinessAttractionReportResidentPopulationInfo.builder().residentPopulation(residentPopulationData).build();
    }

    private BusinessAttractionReportIndexQuarterlyQuotientInfo getIndexQuarterlyQuotientInfo(AdministrativeDistrict district) {
        IndexQuarterlyQuotient indexQuarterlyQuotientData = indexQuarterlyQuotientRepository.findByStandardYearQuarterCodeAndAdministrativeDistrict(
                globalData.getStandardYearQuarterCode(), district
        ).orElseThrow();

        return BusinessAttractionReportIndexQuarterlyQuotientInfo.builder().indexQuarterlyQuotient(indexQuarterlyQuotientData).build();
    }

    private BusinessAttractionReportIncomeConsumptionInfo getIncomeConsumptionInfo(AdministrativeDistrict district) {
        IncomeConsumption incomeConsumptionData = incomeConsumptionRepository.findByStandardYearQuarterCodeAndAdministrativeDistrict(
                globalData.getStandardYearQuarterCode(), district
        ).orElseThrow();

        return BusinessAttractionReportIncomeConsumptionInfo.builder().incomeConsumption(incomeConsumptionData).build();
    }

    private BusinessAttractionReportFloatingPopulationInfo getFloatingPopulationInfo(AdministrativeDistrict district) {
        FloatingPopulation floatingPopulationData = floatingPopulationRepository.findByStandardYearQuarterCodeAndAdministrativeDistrict(
                globalData.getStandardYearQuarterCode(), district
        ).orElseThrow();

        return BusinessAttractionReportFloatingPopulationInfo.builder().floatingPopulation(floatingPopulationData).build();
    }
}
