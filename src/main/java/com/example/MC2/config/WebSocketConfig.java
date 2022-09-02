package com.example.MC2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketConfig
{

    @Value(value = "${my.web-socket.url}")
    private String webSocketUrl;

    private final StompSessionHandler sessionHandler;

    @Autowired
    public WebSocketConfig( StompSessionHandler aSessionHandler )
    {
        sessionHandler = aSessionHandler;
    }

    @Bean
    public StompSessionHandler customStompSessionHandler()
    {
        var stompClient = new WebSocketStompClient( new StandardWebSocketClient() );
        stompClient.setMessageConverter( new MappingJackson2MessageConverter() );

        stompClient.connect( webSocketUrl, sessionHandler );

        return sessionHandler;
    }
}
