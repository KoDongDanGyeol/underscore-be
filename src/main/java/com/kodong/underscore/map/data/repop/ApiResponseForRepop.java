package com.kodong.underscore.map.data.repop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForRepop<T> {
    @JsonProperty("VwsmAdstrdStorW")
    private VwsmAdstrdRepopW<T> vwsmAdstrdRepopW;
}
