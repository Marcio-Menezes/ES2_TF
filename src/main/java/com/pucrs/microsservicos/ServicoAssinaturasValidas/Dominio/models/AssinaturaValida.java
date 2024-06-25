package com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AssinaturaValida {
    @Id
    private Long codAss;
    private LocalDate dataFimVigencia;
}
