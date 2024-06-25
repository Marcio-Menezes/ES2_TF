package com.pucrs.microsservicos.ServicoPagamentos.Dominio.services;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.Assinatura;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.repositories.IRepAssinatura;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServAssValEvent;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.events.PagServCadEvent;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.models.*;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.repositories.*;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicoPagamentos {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private IRepPagamentoServPag repPagamento;

    @Autowired
    private IRepAssinatura repAssinatura;

    private static final Logger logger = LoggerFactory.getLogger(ServicoPagamentos.class);

    public Assinatura buscarAssinaturaPorId(Long id) {
        return repAssinatura.findByCodigo(id);
    }

    public void registrarPagamento(int dia, int mes, int ano, Long codass, float valorPago) {
        Assinatura assOpt = buscarAssinaturaPorId(codass);
        logger.info("Registrando pagamento para a assinatura com ID: {}", codass);

        if (assOpt != null && (valorPago >= assOpt.getAplicativo().getCustoMensal())) {
            logger.info("Assinatura encontrada: {}", assOpt);
                
            // Criação do pagamento
            PagamentoEfetuado pagamento = new PagamentoEfetuado();
            pagamento.setAssinatura(assOpt);
            pagamento.setValorPago(valorPago);

            Calendar calendar = Calendar.getInstance();
            calendar.set(ano, mes-1, dia);
            Date dataPag = calendar.getTime();

            pagamento.setDataPagamento(dataPag);

            // Salva o pagamento no repositório
            repPagamento.save(pagamento);
            logger.info("Pagamento salvo: {}", pagamento);

            // Publica o evento de pagamento
            PagServCadEvent eventCad = new PagServCadEvent(codass, valorPago, dia, mes, ano);
            eventPublisher.publishPagServCadEvent(eventCad);

            //publica outro evento
            PagServAssValEvent eventAss = new PagServAssValEvent(codass, valorPago, dia, mes, ano);
            eventPublisher.publishPagServAssValEvent(eventAss);
        }
    }
    
}
