package com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.listeners;

import com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.repositories.IRepAssinaturaValida;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServAssValEvent;

import static com.pucrs.microsservicos.RabbitMQConfig.QUEUE_ASSINATURAS_VALIDAS;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
public class PagServAssValListener {

    @Autowired
    private IRepAssinaturaValida repAssinaturaValida;

    @RabbitListener(queues = QUEUE_ASSINATURAS_VALIDAS)
    @CacheEvict(value = "assinaturasValidas", key = "#event.codAss")
    public void handlePagServAssValEvent(PagServAssValEvent event) {
        repAssinaturaValida.deleteById(event.getCodAss());
    }
}
