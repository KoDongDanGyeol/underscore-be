package com.kodong.underscore.map.controller;

import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.service.CustomOAuth2UserService;
import com.kodong.underscore.map.data.BusinessAttractionRequestData;
import com.kodong.underscore.map.data.BusinessAttractionResponseDTO;
import com.kodong.underscore.map.data.report.BusinessAttractionReportResponseDTO;
import com.kodong.underscore.map.exception.LogInRequiredException;
import com.kodong.underscore.map.exception.ErrorCode;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import com.kodong.underscore.map.repository.ServiceIndustryRepository;
import com.kodong.underscore.map.service.BusinessAttractionReportService;
import com.kodong.underscore.map.service.ScoreApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class ScoreApiController {

    private final ScoreApiService scoreApiService;
    private final ServiceIndustryRepository serviceIndustryRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AdministrativeDistrictRepository administrativeDistrictRepository;
    private final BusinessAttractionReportService businessAttractionReportService;

    @GetMapping("serviceIndustryData")
    public ResponseEntity<Map<String,String>> allServiceIndustryInfo(){
        Map<String,String> dtos = scoreApiService.allServiceIndustryData();
        scoreApiService.updateThresholds();

        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping("business-attraction")
    public ResponseEntity<BusinessAttractionResponseDTO> businessAttractions(@RequestBody BusinessAttractionRequestData requestData){

        BusinessAttractionResponseDTO dto;
        User currentUser;

        //로그인 및 결제 처리 체크 하는 로직
        try{
            //로그인 한 경우 전체 개업 매력도 점수 넘김
            currentUser = customOAuth2UserService.getCurrentUser();
            dto = scoreApiService.getBusinessAttractionsForLoggedInUser(requestData);

        }catch (RuntimeException e){
            // 로그인 안 한 경우 일부의 개업 매력도 점수만 넘김
            dto = scoreApiService.getBusinessAttractionsForGuestUser(requestData);

        }
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/business-attraction-report")
    public ResponseEntity<Object> businessAttractionReport(@RequestBody BusinessAttractionRequestData requestData){

        BusinessAttractionReportResponseDTO dto;
        User currentUser;
        // 로그인 여부 확인하는 로직
        try{
            //로그인 하고 결제 한 경우 리포트 정보 넘김
            currentUser = customOAuth2UserService.getCurrentUser();
            dto = businessAttractionReportService.getReport(requestData);

        }catch (RuntimeException e){
            // 로그인 안 한 경우 로그인 하도록 로그인 페이지로 이동
            throw new LogInRequiredException(ErrorCode.LOGIN_REQUIRED);
        }
        // todo 결제 안한 경우 확인하는 로직, 및 처리 로직 필요

        dto = businessAttractionReportService.getReport(requestData);
        return ResponseEntity.ok().body(dto);
    }

    // 일시적으로 로그인 체크용 api
    @GetMapping("loggedin")
    public ResponseEntity<Boolean> isLoggedIn(){
        try {
            // 로그인 한 경우 runtimeException 발생 안하므로 처리
            customOAuth2UserService.getCurrentUser();

            return ResponseEntity.ok(true);

        }catch (RuntimeException ex){
            // 로그인 하지 않은 경우 runtimeException 발생
            // 로그인 안한 경우 처리 로직이 여기에
            System.out.println(" 로그인 하지 않은 사용자 입니다.");
            return ResponseEntity.ok(false);
        }
    }

}
