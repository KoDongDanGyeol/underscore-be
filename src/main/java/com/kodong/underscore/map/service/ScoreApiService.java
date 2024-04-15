package com.kodong.underscore.map.service;

import com.kodong.underscore.map.entity.ServiceIndustry;
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

    public Map<String, String> allServiceIndustryData(){
        Map<String,String> dtos = new LinkedHashMap<>();
        Sort sort = Sort.by(Sort.Direction.ASC, "serviceIndustryCode"); // 오름차순 정렬
        List<ServiceIndustry> datas = serviceIndustryRepository.findByServiceIndustryCodeContaining("CS100",sort);
        for(ServiceIndustry data : datas){
            dtos.put(data.getServiceIndustryCode(),data.getServiceIndustryCodeName());
        }
        return dtos;
    }

}
