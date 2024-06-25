package com.pucrs.microsservicos.ServicoPagamentos.Dominio.repositories;

import com.pucrs.microsservicos.ServicoPagamentos.Dominio.models.PagamentoEfetuado;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepPagamentoServPag extends JpaRepository<PagamentoEfetuado, Long> {
    List<PagamentoEfetuado> findByCodigo(Long codigo);
}
