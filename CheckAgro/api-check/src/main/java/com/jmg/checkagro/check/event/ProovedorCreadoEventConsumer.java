package com.jmg.checkagro.check.event;

import com.jmg.checkagro.check.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProovedorCreadoEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROVEEDOR_CREADO)
    public void listen(ProovedorCreadoEventConsumer.Data message){
        System.out.print("NOMBRE DE PROVEEDOR "+ message.getBusinessName());
        //procesamiento
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
