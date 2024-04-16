package com.kodong.underscore.map.controller;

import com.kodong.underscore.auth.service.CustomOAuth2UserService;
import com.kodong.underscore.map.data.BusinessAttractionDTO;
import com.kodong.underscore.map.data.BusinessAttractionRequestData;
import com.kodong.underscore.map.data.BusinessAttractionResponseDTO;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.ServiceIndustry;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import com.kodong.underscore.map.repository.ServiceIndustryRepository;
import com.kodong.underscore.map.service.ScoreApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class ScoreApiController {

    private final ScoreApiService scoreApiService;
    private final ServiceIndustryRepository serviceIndustryRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AdministrativeDistrictRepository administrativeDistrictRepository;

    @GetMapping("serviceIndustryData")
    public ResponseEntity<Map<String,String>> allServiceIndustryInfo(){
        Map<String,String> dtos = scoreApiService.allServiceIndustryData();
        scoreApiService.updateThresholds();

        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("business-attraction")
    public ResponseEntity<BusinessAttractionResponseDTO> businessAttractions(@RequestBody BusinessAttractionRequestData requestData){
        //TODO 로그인 및 결제 처리 체크 하는 로직
        // boolean loggedIn = customOAuth2UserService.getCurrentUser() != null;

//        if(!loggedIn) {
//            BusinessAttractionResponseDTO dto = scoreApiService.getBusinessAttractionsForGuestUser(requestData);
//            return ResponseEntity.ok().body(dto);
//        }

        BusinessAttractionResponseDTO dto = scoreApiService.getBusinessAttractionsForLoggedInUser(requestData);

        return ResponseEntity.ok().body(dto);


    }

}
