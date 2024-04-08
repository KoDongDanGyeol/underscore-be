package com.kodong.underscore.map.data.selng;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForSelng<T> {
    @JsonProperty("VwsmAdstrdSelngW")
    private VwsmAdstrdSelngW<T> vwsmAdstrdSelngW;
}
