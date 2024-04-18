package com.kodong.underscore.map.data.SGIS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SGISLocationResult {
    @JsonProperty("totalcount")
    private String totalCount;

    @JsonProperty("resultdata")
    private List<AddressToLocationDTO> resultData;

    @JsonProperty("matching")
    private String matching;

    @JsonProperty("pagenum")
    private String pagenum;

    @JsonProperty("returncount")
    private String returnCount;
}
