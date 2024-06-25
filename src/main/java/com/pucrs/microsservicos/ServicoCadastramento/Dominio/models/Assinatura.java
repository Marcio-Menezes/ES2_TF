package com.pucrs.microsservicos.ServicoCadastramento.Dominio.models;

import java.time.LocalDate;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.dto.AssinaturaDTO;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "cod_App")
    private Aplicativo aplicativo;

    @ManyToOne
    @JoinColumn(name = "cod_Cli")
    private Cliente cliente;

    private LocalDate inicioVigencia;
    private LocalDate fimVigencia;

    public AssinaturaDTO convertToDTO() {
        AssinaturaDTO dto = new AssinaturaDTO();
        dto.setCodigo(this.codigo);
        dto.setCliente(this.cliente);
        dto.setCodApp(this.aplicativo);
        dto.setInicioVigencia(this.inicioVigencia);
        dto.setFimVigencia(this.fimVigencia);
        dto.setStatus(this.fimVigencia.isAfter(LocalDate.now()) ? "ATIVA" : "CANCELADA");
        return dto;
    }

}
