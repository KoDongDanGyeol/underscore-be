package com.kodong.underscore.map.data.SGIS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SGISTokenResult {

    @JsonProperty("accessTimeout")
    private String accessTimeout;

    @JsonProperty("accessToken")
    private String accessToken;
}
