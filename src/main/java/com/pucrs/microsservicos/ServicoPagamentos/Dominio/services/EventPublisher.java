package com.pucrs.microsservicos.ServicoPagamentos.Dominio.services;

import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServCadEvent;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServAssValEvent; // Adicione esta linha
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishPagServCadEvent(PagServCadEvent event) {
        rabbitTemplate.convertAndSend("pagamentos.cadastramento", event);
    }

    public void publishPagServAssValEvent(PagServAssValEvent event) {
        rabbitTemplate.convertAndSend("pagamentos.assinaturasvalidas", event);
    }
}
