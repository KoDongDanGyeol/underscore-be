package com.kodong.underscore.map.controller;

import com.kodong.underscore.map.service.ScoreApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/map")
public class ScoreApiController {

    private final ScoreApiService scoreApiService;

    @GetMapping("serviceIndustryData")
    public ResponseEntity<Map<String,String>> allServiceIndustryInfo(){
        Map<String,String> dtos = scoreApiService.allServiceIndustryData();
        scoreApiService.updateThresholds();

        return ResponseEntity.ok().body(dtos);
    }

}
