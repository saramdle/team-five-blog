package com.example.demo.post.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestModel {

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("message")
    private String message;
}
