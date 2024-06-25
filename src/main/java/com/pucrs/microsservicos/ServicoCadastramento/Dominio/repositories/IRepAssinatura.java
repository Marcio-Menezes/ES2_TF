package com.pucrs.microsservicos.ServicoCadastramento.Dominio.repositories;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.Assinatura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepAssinatura extends JpaRepository<Assinatura, Long> {
    
    @Query("SELECT c FROM Assinatura c WHERE c.codigo = :codigo")
    public Assinatura findByCodigo(Long codigo);
    
    public List<Assinatura> findByCliente_Codigo(Long codCli);
    public List<Assinatura> findByAplicativo_Codigo(Long codApp);

}
