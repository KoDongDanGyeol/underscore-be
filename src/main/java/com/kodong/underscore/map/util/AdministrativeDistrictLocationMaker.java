package com.kodong.underscore.map.util;

import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.data.SGIS.AddressToLocationDTO;
import com.kodong.underscore.map.data.SGIS.Response;
import com.kodong.underscore.map.data.SGIS.SGISLocationResult;
import com.kodong.underscore.map.data.SGIS.SGISTokenResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 행정복지센터 주소를 읽어 좌표를 얻고 해당 좌표를 행정동의 대표 좌표로 활용
 * api로 얻은 좌표를 csv에 저장
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AdministrativeDistrictLocationMaker {

    private final GlobalData globalData;
    private final DataUpdate dataUpdate;

    public String refreshSGISAccessToken(){
        RestClient restClient = RestClient.builder().build();

        Response<SGISTokenResult> response = restClient.get()
                .uri("")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if(response.getSgisResult() == null){
            log.info("SGIS Token 요청 결과 넘어온 응답이 NULL 입니다");
        }
        globalData.updateTokenForSGIS(response.getSgisResult().getAccessToken());

        return response.getSgisResult().getAccessToken();
    }

    public AddressToLocationDTO getLocation(String address){
        RestClient restClient = RestClient.builder().build();

        Response<SGISLocationResult> response = restClient.get()
                .uri(uriMaker(globalData.getTokenForSGIS(), address))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (response != null && response.getSgisResult() != null) {
            if (response.getSgisResult().getResultData() == null) {
                log.info("{} 이 주소의 결과가 NULL 입니다.", address);
                return null;
            }
        } else {
            log.warn("Response 또는 SgisResult가 NULL 입니다. 처리할 수 없습니다. 주소: {}", address);
            return null;
        }

        if(response.getSgisResult().getResultData().size()>1){
            log.info("{} 이 주소의 결과가 여러 개 입니다.", address);
        }

        AddressToLocationDTO dto = response.getSgisResult().getResultData().get(0);
        if(dataUpdate.getAdministrativeClassificationToLocation().contains(dto.getAdmCd())){
            dto.setAdmCd(
                    dataUpdate.getAdministrativeClassificationUpdateData().get(dto.getAdmCd())
            );
        }

        return dto;
    }

    public URI uriMaker(String accessToken, String address) {
        return UriComponentsBuilder
                .fromHttpUrl("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocodewgs84.json")
                .queryParam("accessToken", accessToken)
                .queryParam("address", address)
                .build()
                .toUri();
    }

}
