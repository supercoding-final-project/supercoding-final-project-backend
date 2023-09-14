package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.ImageDto;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.service.S3Service;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import com.github.supercodingfinalprojectbackend.util.ValidateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@Tag(name = "이미지 API")
public class ImageFileController {

    private final S3Service s3Service;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 파일 업로드")
    private ResponseEntity<ResponseUtils.ApiResponse<ImageDto.UrlMapResponse>> uploadImageFile(
            @RequestParam(value = "imageFile") @Parameter(name = "이미지 파일", required = true) List<MultipartFile> imageFiles
    ) throws InterruptedException {
        ValidateUtils.requireTrue(imageFiles != null && !imageFiles.isEmpty(), ApiErrorCode.INVALID_REQUEST_BODY);

        ImageDto.UrlMapResponse response = s3Service.uploadImageFiles(imageFiles);

        return ResponseUtils.created("이미지가 성공적으로 업로드되었습니다.", response);
    }
}
