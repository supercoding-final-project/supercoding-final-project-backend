package com.github.supercodingfinalprojectbackend.controller;


import com.github.supercodingfinalprojectbackend.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("api/v1/s3/example")
public class S3ControllerExample {
    private final S3Service s3Service;

    @PostMapping()
    private void getS3UrlExample(@RequestParam("file") MultipartFile file)  {

        String url = s3Service.uploadFile(file); // S3 Url 을 가져오는 메서드입니다.

        s3Service.deleteFile(url); // S3 에 저장된 파일을 삭제하는 메서드입니다.

    }
}
