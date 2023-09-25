package com.github.supercodingfinalprojectbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/ws"); // 클라이언트가 메시지를 송신할 엔드포인트의 접두사입니다.
        config.enableSimpleBroker("/chatroom"); // 메시지 브로커를 설정 ("/chatroom"로 시작하는 주제를 사용)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/code-velop")// SockJs 연결 주소
                .setAllowedOriginPatterns("*")
                .withSockJS();// 낮은 버전에서도 사용 가능헤가 하는 설정
    }
}