package com.example.MC2.handlers;

import com.example.MC2.dto.MessageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.Date;

@Service
public class MyStompSessionHandler extends StompSessionHandlerAdapter
{

    @Value(value = "${my.kafka.topic}")
    private String topic;

    @Value("${my.web-socket.topic}")
    private String destination;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public MyStompSessionHandler( KafkaTemplate<String, String> aKafkaTemplate, ObjectMapper aObjectMapper )
    {
        kafkaTemplate = aKafkaTemplate;
        objectMapper = aObjectMapper;
        objectMapper.setSerializationInclusion( JsonInclude.Include.NON_NULL);
    }

    @Override
    public void handleFrame( StompHeaders headers, Object payload )
    {
        var message = ( MessageDTO ) payload;
        message.setMC2Timestamp( new Date() );
        try
        {
            kafkaTemplate.send( topic, objectMapper.writeValueAsString( message ) );
        } catch ( JsonProcessingException e )
        {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void afterConnected( StompSession session, StompHeaders connectedHeaders )
    {
        session.subscribe( destination, this );
        System.out.println( "connected success!" );
    }

    @Override
    public void handleException( StompSession session,
                                 StompCommand command,
                                 StompHeaders headers,
                                 byte[] payload,
                                 Throwable exception )
    {
        System.out.printf( "Got an exception: %s", exception );
    }

    @Override
    public void handleTransportError( StompSession session, Throwable exception )
    {
        System.out.printf( "Got an exception: %s", exception );
    }

    @Override
    public Type getPayloadType( StompHeaders headers )
    {
        return MessageDTO.class;
    }
}
