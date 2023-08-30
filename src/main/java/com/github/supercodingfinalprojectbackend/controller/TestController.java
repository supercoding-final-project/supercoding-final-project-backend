package com.github.supercodingfinalprojectbackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/v1/test")
@Tag(name = "테스트용 API", description = "테스트를 위해 작성된 API입니다.")
public class TestController {

}