package com.pucrs.microsservicos.ServicoPagamentos.Dominio.models;

import java.util.Date;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.Assinatura;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PagamentoEfetuado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;
    @ManyToOne
    @JoinColumn(name = "cod_Assinatura")
    private Assinatura assinatura;
    private Float valorPago;
    private Date dataPagamento;
    
}
