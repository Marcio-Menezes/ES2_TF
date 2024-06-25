package com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.repositories;

import com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.models.AssinaturaValida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepAssinaturaValida extends JpaRepository<AssinaturaValida, Long> {
}
