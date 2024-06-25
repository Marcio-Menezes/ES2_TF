package com.pucrs.microsservicos.ServicoCadastramento.Dominio.repositories;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepCliente extends JpaRepository<Cliente, Long> {
    public Cliente findByNome(String nome);
    public Cliente save(Cliente cliente);

    @Query("SELECT c FROM Cliente c WHERE c.codigo = :codigo")
    public Cliente buscarClientePorCodigo(@Param("codigo") Long codigo);
}
