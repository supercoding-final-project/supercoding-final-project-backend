package com.github.supercodingfinalprojectbackend.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;
    private final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of(".png", ".jpg", ".jpeg");

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private void validateFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (validateImageExtension(extension)) return;

            throw new ApiException(ApiErrorCode.IMAGE_EXTENSION_BAD_REQUEST);
        } catch (StringIndexOutOfBoundsException e) {
            throw new ApiException(ApiErrorCode.IMAGE_INVALID_FORMAT);
        }
    }
    public void deleteFile(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath();
            String filename = path.substring(path.lastIndexOf('/') + 1);

            amazonS3Client.deleteObject(bucket, filename);

        } catch (MalformedURLException e){
            e.printStackTrace();
        }
    }

    public String uploadImageFile(MultipartFile file) throws IOException {
        if (!validateImageFiles(file)) throw ApiErrorCode.IMAGE_INVALID_FORMAT.exception();
        return upload(file);
    }
    public Map<Integer, String> uploadImageFiles(MultipartFile... files) throws InterruptedException {
        if (!validateImageFiles(files)) throw ApiErrorCode.IMAGE_INVALID_FORMAT.exception();

        final int numberOfThreads = files.length;
        final CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ConcurrentHashMap<Integer, String> result = new ConcurrentHashMap<>(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            final int index = i;
            MultipartFile file = files[index];

            new Thread(()->{
                try {
                    String url = upload(file);
                    result.put(index, url);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            }).start();
        }
        latch.await();

        return result;
    }

    private boolean validateImageFiles(MultipartFile... files) {
        Objects.requireNonNull(files);

        return Arrays.stream(files)
                .map(this::parseExtension)
                .allMatch(this::validateImageExtension);
    }

    private String parseExtension(MultipartFile file) {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private Boolean validateImageExtension(String extension) {
        return ALLOWED_IMAGE_EXTENSIONS.contains(extension);
    }

    private String upload(MultipartFile file) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(file.getContentType());
        meta.setContentLength(file.getSize());

        String fileName = file.getOriginalFilename();

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), meta);

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
