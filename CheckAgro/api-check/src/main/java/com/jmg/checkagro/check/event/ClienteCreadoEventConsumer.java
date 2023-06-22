package com.jmg.checkagro.check.event;

import com.jmg.checkagro.check.config.RabbitMQConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteCreadoEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CLIENTE_CREADO)
    public void listen(ClienteCreadoEventConsumer.Data message){


        System.out.print("\n NOMBRE DE CLIENTE "+ message.getId());
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
