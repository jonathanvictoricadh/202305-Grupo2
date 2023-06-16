package com.jmg.checkagro.check.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "backendExchange";

    public static final String TOPIC_PROVEEDOR_CREADO = "com.dh.proveedorCreado";
    public static final String QUEUE_PROVEEDOR_CREADO ="queueProveedorCreado";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queueProveedorCreado(){
        return new Queue(QUEUE_PROVEEDOR_CREADO);
    }

    @Bean
    public Binding declareBindingSpecific(){
        return BindingBuilder.bind(queueProveedorCreado()).to(appExchange()).with(TOPIC_PROVEEDOR_CREADO);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
