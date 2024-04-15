package com.kodong.underscore.map.data.ncmcnsmp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForNcmCnsmp<T> {
    @JsonProperty("VwsmAdstrdNcmCnsmpW")
    private VwsmAdstrdNcmCnsmpW<T> vwsmAdstrdNcmCnsmpW;
}
