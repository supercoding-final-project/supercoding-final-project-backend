package com.github.supercodingfinalprojectbackend.controller;

import com.github.supercodingfinalprojectbackend.dto.response.PageResponse;
import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import com.github.supercodingfinalprojectbackend.util.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


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

    private void exceptionExample() {
        // 1. 여러분이 코드를 작성하는 중에 컨트롤 가능한 모든 예외는 ApiException으로 던져주셔야 합니다.

        // 2. ApiException을 던질 때는 '상태코드'와 '메세지'를 매개변수로 넣어줄 수 있습니다.
        //    '상태코드'는 HttpStatus와 int 둘 다 사용 가능합니다.
        ApiException exception1 = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "서버의 문제~~");
        ApiException exception2 = new ApiException(500, "서버의 문제~~~~");

        // 3. 만약 자주 사용하게 되는 상태코드와 메세지가 있다면 ErrorCode에 추가하여 재사용할 수 있습니다.
        ApiException exception3 = new ApiException(ApiErrorCode.FAIL_TO_SAVE);

        // 4. ErrorCode를 사용해서 바로 예외를 던질 수도 있습니다.
        ApiException exception4 = ApiErrorCode.FAIL_TO_SAVE.exception();

        // 5. ErrorCode를 사용해서 예외를 바로 던지는 방식은 Optional과 결합했을 때 꽤 편합니다.
        //    아래의 두 코드를 비교해보세요
        String str1 = Optional.ofNullable("hello, world").orElseThrow(()->new ApiException(ApiErrorCode.FAIL_TO_SAVE));
        String str2 = Optional.ofNullable("hello, world").orElseThrow(ApiErrorCode.FAIL_TO_SAVE::exception);

        // 6. 마지막으로 원래 의도는 ErrorCode를 다양하게 만들어서 사용하려고 했는데
        //    아무래도 재사용할 에러코드와 메세지가 생각보다 많지 않고
        //    또 ErrorCode를 새로 만드는 과정이 번거롭다보니 접근성이 떨어지는 것 같습니다.
        //    그래서 이제 ApiErrorCode만 사용하는 걸로 하겠습니다.
    }
}