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


@Service
@Slf4j
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {
        try {
            String S3Url;
            String fileName= file.getOriginalFilename();
            getFileExtension(fileName);
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,fileName,file.getInputStream(),metadata);

            return S3Url = String.valueOf(amazonS3Client.getUrl(bucket,fileName));
//           TODO :: 업로드 한거 삭제하는 service , 파일이름 UUID 고려
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void getFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
            if (extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")) {
                return ;
            }
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
}
