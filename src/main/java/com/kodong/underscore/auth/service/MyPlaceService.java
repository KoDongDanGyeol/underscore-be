package com.kodong.underscore.auth.service;

import com.kodong.underscore.auth.dto.MyPlaceDto;
import com.kodong.underscore.auth.entity.MyPlace;
import com.kodong.underscore.auth.entity.User;
import com.kodong.underscore.auth.repository.MyPlaceRepository;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MyPlaceService {

    private final MyPlaceRepository myPlaceRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final AdministrativeDistrictRepository administrativeDistrictRepository;

    @Transactional
    public MyPlace save(Long placeId, String memo) {
        User currentUser = customOAuth2UserService.getCurrentUser();

        // 중복 check
        MyPlace findMyPlace = myPlaceRepository.findByPlaceIdAndUserId(placeId, currentUser.getId()).orElse(null);
        if (!(findMyPlace == null))
            throw new RuntimeException("이미 추가된 장소입니다.");

        AdministrativeDistrict place = administrativeDistrictRepository.findById(placeId).orElseThrow();
        return myPlaceRepository.save(new MyPlace(currentUser, place, memo));
    }

    // 내 장소 리스트 조회
    public List<MyPlaceDto> findMyPlaces() {
        User currentUser = customOAuth2UserService.getCurrentUser();

        List<MyPlace> myPlaces = myPlaceRepository.findMyPlaces(currentUser.getId());
        return myPlaces.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 단건 삭제
    @Transactional
    public void delete(Long placeId) {
        User currentUser = customOAuth2UserService.getCurrentUser();
        MyPlace findMyPlace = myPlaceRepository.findByPlaceIdAndUserId(placeId, currentUser.getId()).orElse(null);
        if (!(findMyPlace == null)) {
            myPlaceRepository.delete(findMyPlace);
        }
    }

    private MyPlaceDto convertToDTO(MyPlace myPlace) {
        MyPlaceDto myPlaceDto = new MyPlaceDto();
        myPlaceDto.setSiDo(myPlace.getAdministrativeDistrict().getSiDo());
        myPlaceDto.setSiGunGu(myPlace.getAdministrativeDistrict().getSiGunGu());
        myPlaceDto.setEupMyeonDong(myPlace.getAdministrativeDistrict().getEupMyeonDong());
        return myPlaceDto;
    }
}
