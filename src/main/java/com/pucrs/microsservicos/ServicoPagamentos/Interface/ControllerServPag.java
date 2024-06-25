package com.pucrs.microsservicos.ServicoPagamentos.Interface;

import com.pucrs.microsservicos.ServicoPagamentos.Dominio.models.PagamentoRequest;
import com.pucrs.microsservicos.ServicoPagamentos.Dominio.services.ServicoPagamentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerServPag {

    @Autowired
    private ServicoPagamentos servicoPagamentos;

    @PostMapping("/registrarpagamento")
    public void registrarPagamento(@RequestBody PagamentoRequest pagamentoRequest) {
        servicoPagamentos.registrarPagamento(pagamentoRequest.getDia(), pagamentoRequest.getMes(), pagamentoRequest.getAno(),
                                                            pagamentoRequest.getCodass(), pagamentoRequest.getValorPago());
    }
}
