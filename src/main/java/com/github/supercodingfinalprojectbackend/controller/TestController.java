package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.response.PageResponse;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/api/v1/test")
@Tag(name = "테스트용 API", description = "테스트를 위해 작성된 API입니다.")
public class TestController {
    @GetMapping("/")
    @Operation(summary = "스웨거 정상 동작 테스트")
    public Object test() {
        // ResponseUtils로 생성된 응답 객체는 모두 ResponseEntity<?>입니다.

        PageResponse<String> data = new PageResponse<>();

        // 가장 기본적인 사용법
        // 상태코드, 메세지, 전달할 데이터를 입력
        ResponseEntity<?> response1 = ResponseUtils.status(HttpStatus.NO_CONTENT, "응답 메세지", data);

        // 자주 사용되는 상태코드에 대응되는 메서드가 이미 있습니다.
        ResponseEntity<?> response2 = ResponseUtils.ok("응답 메세지", data);
        ResponseEntity<?> response3 = ResponseUtils.notFound("응답 메세지", data);
        ResponseEntity<?> response4 = ResponseUtils.internalServerError("응답 메세지", data);

        // 헤더를 커스텀하고 싶은 경우 빌더 패턴을 사용할 수 있습니다.
        // 빌더는 status() 메서드의 호출로 생성할 수 있습니다.
        ResponseEntity<?> response5 = ResponseUtils.status(HttpStatus.NO_CONTENT)
                .message("빌더 패턴에서 메세지를 작성하지 않으면 빈 문자열이 내려갑니다.")
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);    // body()를 호출하면 객체가 빌드됩니다. 전달할 데이터가 없다면 build()를 호출해도 됩니다.

        // 빌더 패턴 역시 자주 사용되는 상태 코드에 대응되는 메서드가 이미 있습니다.
        ResponseEntity<?> response6 = ResponseUtils.created()
                .message("빌더 패턴에서 메세지를 작성하지 않으면 빈 문자열이 내려갑니다.")
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);

        return response6;
    }

    @GetMapping("/ga")
    public String gaTest() {
        return "gitAction 성공";
    }
}