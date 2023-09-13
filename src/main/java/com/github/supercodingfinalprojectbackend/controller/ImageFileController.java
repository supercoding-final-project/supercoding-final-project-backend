package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.ImageFileDto;
import com.github.supercodingfinalprojectbackend.service.S3Service;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageFileController {

    private final S3Service s3Service;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 파일 업로드")
    private ResponseEntity<ResponseUtils.ApiResponse<ImageFileDto.ImageUrlResponse>> uploadImageFile(
            @RequestParam(value = "imageFile") @Parameter(name = "이미지 파일", required = true) MultipartFile imageFile
    ) throws IOException {
        String imageUrl = s3Service.uploadImageFile(imageFile);
        ImageFileDto.ImageUrlResponse response = ImageFileDto.ImageUrlResponse.from(imageUrl);

        return ResponseUtils.created("이미지가 성공적으로 업로드되었습니다.", response);
    }
}
