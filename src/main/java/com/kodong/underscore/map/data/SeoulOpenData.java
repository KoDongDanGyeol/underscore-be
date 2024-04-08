package com.kodong.underscore.map.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SeoulOpenData<T> {
    @JsonProperty("list_total_count")
    private int list_total_count;

    @JsonProperty("RESULT")
    private Result RESULT;

    @JsonProperty("row")
    private List<T> row;
}
