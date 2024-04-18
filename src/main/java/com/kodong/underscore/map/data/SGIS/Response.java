package com.kodong.underscore.map.data.SGIS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response<T> {
    @JsonProperty("id")
    private String id;

    @JsonProperty("result")
    private T sgisResult;

    @JsonProperty("errMsg")
    private String errMsg;

    @JsonProperty("errCd")
    private String errCd;

    @JsonProperty("trId")
    private String trId;

}
