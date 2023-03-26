package com.example.demo.common.Bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultModel {

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("message")
    private String message;
}
