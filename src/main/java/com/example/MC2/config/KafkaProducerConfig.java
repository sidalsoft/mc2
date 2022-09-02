package com.example.MC2.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;


@Configuration
public class KafkaProducerConfig
{

    @Value(value = "${my.kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${my.kafka.topic}")
    private String topic;

    @Bean
    public KafkaAdmin kafkaAdmin()
    {
        var configs = new HashMap<String, Object>();
        configs.put( AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        return new KafkaAdmin( configs );
    }

    @Bean
    public NewTopic topic1()
    {
        return new NewTopic( topic, 1, ( short ) 1 );
    }

    @Bean
    public ProducerFactory<String, String> producerFactory()
    {
        var configProps = new HashMap<String, Object>();
        configProps.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        configProps.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
        configProps.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
        return new DefaultKafkaProducerFactory<>( configProps );
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate()
    {
        return new KafkaTemplate<>( producerFactory() );
    }
}