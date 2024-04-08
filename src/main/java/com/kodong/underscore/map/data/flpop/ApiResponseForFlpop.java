package com.kodong.underscore.map.data.flpop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForFlpop<T> {
    @JsonProperty("VwsmAdstrdFlpopW")
    private VwsmAdstrdFlpopW<T> vwsmAdstrdFlpopW;
}
