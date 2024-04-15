package com.kodong.underscore.map.data.ixqq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseForIxqq<T> {
    @JsonProperty("VwsmAdstrdIxQq")
    private VwsmAdstrdIxQq<T> vwsmAdstrdIxQq;
}
