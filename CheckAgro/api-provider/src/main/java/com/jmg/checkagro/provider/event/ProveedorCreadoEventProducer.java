package com.jmg.checkagro.provider.event;

import com.jmg.checkagro.provider.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProveedorCreadoEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public ProveedorCreadoEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishCrearProveedor(ProveedorCreadoEventProducer.Data message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_PROVEEDOR_CREADO,message);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private Long id;
        private String businessName;

    }
}
