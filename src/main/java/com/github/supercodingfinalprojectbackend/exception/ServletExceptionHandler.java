package com.github.supercodingfinalprojectbackend.exception;

import com.github.supercodingfinalprojectbackend.dto.ErrorMessageDto;
import com.github.supercodingfinalprojectbackend.dto.WebhookDto;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ServletExceptionHandler {
    @Value("${error-webhook-url}")
    private String webhookUrl;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        sendMessage(e.getMessage(),e);
        return ResponseUtils.status(e.getStatus(), e.getMessage(), null);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> PostApiException() {
        return ResponseUtils.badRequest("입력값이 올바르지 않습니다.", null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException e) {
        return ResponseUtils.badRequest(
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }

    public void sendMessage(String msg, Exception e) {
        List<StackTraceElement> errorList = Arrays.stream(e.getStackTrace()).filter(error->error.getClassName().contains("supercodingfinalprojectbackend")).collect(Collectors.toList());
        List<String> stringList = errorList.stream().map(StackTraceElement::toString).collect(Collectors.toList());

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/json; utf-8");

            List<ErrorMessageDto> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder("------------------------------ 호출스택 ------------------------------\n");

            for (String str : stringList) {
                String[] arr = str.split("\\.",5);
                sb.append(arr[4]).append("\n");
            }
            ErrorMessageDto resDto = new ErrorMessageDto(msg, sb.toString());
            list.add(resDto);
            HttpEntity<WebhookDto> messageEntity = new HttpEntity<>(new WebhookDto(list), httpHeaders);

            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.exchange(
                    webhookUrl,
                    HttpMethod.POST,
                    messageEntity,
                    String.class
            );

            // response에 대한 처리
            if (response.getStatusCode().value() != HttpStatus.NO_CONTENT.value()) {
                log.error("메시지 전송 이후 에러 발생");
            }

        } catch (Exception ex) {
            log.error("에러 발생 :: " + ex);
        }
    }
}
