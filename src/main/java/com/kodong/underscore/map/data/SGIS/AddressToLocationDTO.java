package com.kodong.underscore.map.data.SGIS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressToLocationDTO {

    @JsonProperty("ri_nm")
    private String riNm;

    @JsonProperty("road_nm_sub_no")
    private String roadNmSubNo;

    @JsonProperty("sgg_cd")
    private String sggCd;

    @JsonProperty("adm_cd")
    private String admCd;

    @JsonProperty("road_nm_main_no")
    private String roadNmMainNo;

    @JsonProperty("leg_cd")
    private String legCd;

    @JsonProperty("road_nm")
    private String roadNm;

    @JsonProperty("bd_matches")
    private String bdMatches;

    @JsonProperty("bd_sub_nm")
    private String bdSubNm;

    @JsonProperty("addr_type")
    private String addrType;

    @JsonProperty("sido_nm")
    private String sidoNm;

    @JsonProperty("sgg_nm")
    private String sggNm;

    @JsonProperty("sido_cd")
    private String sidoCd;

    @JsonProperty("road_cd")
    private String roadCd;

    @JsonProperty("bd_main_nm")
    private String bdMainNm;

    @JsonProperty("adm_nm")
    private String admNm;

    @JsonProperty("jibun_main_no")
    private String jibunMainNo;

    @JsonProperty("origin_xy")
    private String originXy;

    @JsonProperty("jibun_sub_no")
    private String jibunSubNo;

    @JsonProperty("ri_cd")
    private String riCd;

    @JsonProperty("y")
    private String y;

    @JsonProperty("leg_nm")
    private String legNm;

    @JsonProperty("x")
    private String x;
}
