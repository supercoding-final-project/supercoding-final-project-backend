package com.github.supercodingfinalprojectbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WebhookDto {
//
//    @JsonProperty("content")
//    private String content;

    @JsonProperty("embeds")
    private List<ErrorMessageDto> embeds;
}
