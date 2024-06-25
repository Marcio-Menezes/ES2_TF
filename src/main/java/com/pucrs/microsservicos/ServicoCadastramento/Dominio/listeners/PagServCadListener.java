package com.pucrs.microsservicos.ServicoCadastramento.Dominio.listeners;

import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServCadEvent;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.Assinatura;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.repositories.IRepAssinatura;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.services.ServicoCadastramento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.pucrs.microsservicos.RabbitMQConfig.QUEUE_CADASTRAMENTO;


@Component
public class PagServCadListener {

    @Autowired
    private IRepAssinatura repAssinatura;

    private static final Logger logger = LoggerFactory.getLogger(ServicoCadastramento.class);

    @RabbitListener(queues = QUEUE_CADASTRAMENTO)
    public void handlePagServCadEvent(PagServCadEvent event) {
        Assinatura assinatura = repAssinatura.findByCodigo(event.getCodAss());
        logger.info("Lidando com evento ServCad");

        if (assinatura != null) {
            logger.info("Assinatura encontrada");

            assinatura.setFimVigencia(assinatura.getFimVigencia().plusMonths(1));
            logger.info("Vigencia atualizada para {}", assinatura.getFimVigencia());
            
            repAssinatura.save(assinatura);
        }
    }
}
