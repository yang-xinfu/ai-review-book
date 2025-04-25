package com.damai.aireviewbook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AIReviewResult {

    @JsonProperty("content")
    private String content;

    @JsonProperty("suggest")
    private String suggest;

    @JsonProperty("type")
    private String type;

}
