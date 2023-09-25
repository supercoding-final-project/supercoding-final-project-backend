package com.github.supercodingfinalprojectbackend.service;

import com.github.supercodingfinalprojectbackend.exception.ApiException;
import com.github.supercodingfinalprojectbackend.exception.errorcode.ApiErrorCode;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;
    private OpenVidu openVidu;

    @PostConstruct
    public void initSession() {
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    //화상연결 버튼 클릭시 해당 세션을 만들어줌
    //연결 대상과 맞는 세션아이디를 비교해서 줘야하는가? -> 채팅방안에서 화상채팅 연결을 할 것이기 때문에 대상
    public String createSession(Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openVidu.createSession(properties);
        return session.getSessionId();
    }

    //TODO 당일 연결에 대해  결제 안 한 사람의 경우 추가 예정(결제 내역)

    //연결 할 때 (토큰을 준다)
    public String connection(String sessionId, Map<String, Object> params)
        throws OpenViduJavaClientException, OpenViduHttpException {
        {
            Session session = openVidu.getActiveSession(sessionId);
            if (session == null) {
                throw new ApiException(ApiErrorCode.INVALID_CONNECTION_REQUEST);
            }
            ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
            Connection connection = session.createConnection(properties);
            return connection.getToken();
        }
    }
    //연결 끊기(세션 Id를 삭제 해야하는가? => 1회성 연결이라 세션이 닫힘 그렇다면 동일한 세션)
}