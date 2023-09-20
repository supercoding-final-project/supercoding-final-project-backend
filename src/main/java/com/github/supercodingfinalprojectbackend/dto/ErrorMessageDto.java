package com.github.supercodingfinalprojectbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorMessageDto {
    private String title;
    private String description;
}
