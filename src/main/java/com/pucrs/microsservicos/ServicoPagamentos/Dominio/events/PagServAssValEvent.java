package com.pucrs.microsservicos.ServicoPagamentos.Dominio.events;

import lombok.Getter;

@Getter
public class PagServAssValEvent {
    private Long codAss;
    private float valorPago;
    private int dia;
    private int mes;
    private int ano;

    public PagServAssValEvent(Long codAss, float valorPago, int dia, int mes, int ano) {
        this.codAss = codAss;
        this.valorPago = valorPago;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
}
