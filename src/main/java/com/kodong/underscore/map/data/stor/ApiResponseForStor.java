package com.kodong.underscore.map.data.stor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForStor<T> {
    @JsonProperty("VwsmAdstrdStorW")
    private VwsmAdstrdStorW<T> vwsmAdstrdStorW;
}
