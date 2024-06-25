package com.pucrs.microsservicos.ServicoPagamentos.Dominio.models;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoRequest {
    private int dia;
    private int mes;
    private int ano;
    private Long codass;
    private float valorPago;
}