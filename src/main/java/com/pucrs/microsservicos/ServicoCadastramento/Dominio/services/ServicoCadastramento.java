package com.pucrs.microsservicos.ServicoCadastramento.Dominio.services;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.*;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicoCadastramento {
    @Autowired
    private IRepCliente repCliente;

    @Autowired
    private IRepAssinatura repAssinatura;

    @Autowired
    private IRepPagamento repPagamento;

    @Autowired
    private IRepAplicativo repAplicativo;


    public ServicoCadastramento(IRepCliente repCliente, IRepAssinatura repAssinatura, IRepPagamento repPagamento, IRepAplicativo repAplicativo) {
        this.repCliente = repCliente;
        this.repAssinatura = repAssinatura;
        this.repPagamento = repPagamento;
        this.repAplicativo = repAplicativo;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        return repCliente.save(cliente);
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return repCliente.findById(id);
    }

    public Cliente atualizarCliente(Cliente cliente) {
        return repCliente.save(cliente);
    }

    public void deletarCliente(Long id) {
        repCliente.deleteById(id);
    }

    public Aplicativo cadastrarAplicativo(Aplicativo aplicativo) {
        return repAplicativo.save(aplicativo);
    }

    public Optional<Aplicativo> buscarAplicativoPorId(Long id) {
        return repAplicativo.findById(id);
    }

    public Aplicativo atualizarAplicativo(Long id, Aplicativo novosDados) {
        Optional<Aplicativo> aplicativoExistenteOpt = repAplicativo.findById(id);

        if (aplicativoExistenteOpt.isPresent()) {
            Aplicativo aplicativoExistente = aplicativoExistenteOpt.get();
            
            aplicativoExistente.setNome(novosDados.getNome());
            aplicativoExistente.setCustoMensal(novosDados.getCustoMensal());
            
            return repAplicativo.save(aplicativoExistente);
        } else {
            throw new RuntimeException("Aplicativo não encontrado com o ID: " + id);
        }
    }

    public void deletarAplicativo(Long id) {
        repAplicativo.deleteById(id);
    }

    public Assinatura cadastrarAssinatura(Long clienteId, Long aplicativoCodigo) {
        Cliente cli = repCliente.buscarClientePorCodigo(clienteId);
        Aplicativo app = repAplicativo.buscarAplicativoPorCodigo(aplicativoCodigo);

        if (cli!=null && app!=null) {
            Assinatura ass = new Assinatura();
            ass.setCliente(cli);
            ass.setAplicativo(app);
            ass.setInicioVigencia(LocalDate.now());
            ass.setFimVigencia(LocalDate.now().plusDays(7)); // 7 dias grátis

            return repAssinatura.save(ass);

        }
        else {
            throw new RuntimeException("Cliente ou Aplicativo não encontrado");
        }
    }

    public Optional<Assinatura> buscarAssinaturaPorId(Long id) {
        return repAssinatura.findById(id);
    }

    public Assinatura atualizarAssinatura(Long id, Assinatura novosDados) {
        Optional<Assinatura> assinaturaExistenteOpt = repAssinatura.findById(id);

        if (assinaturaExistenteOpt.isPresent()) {
            Assinatura assinaturaExistente = assinaturaExistenteOpt.get();
            
            assinaturaExistente.setAplicativo(novosDados.getAplicativo());
            assinaturaExistente.setCliente(novosDados.getCliente());
            assinaturaExistente.setInicioVigencia(novosDados.getInicioVigencia());
            assinaturaExistente.setFimVigencia(novosDados.getFimVigencia());
            
            return repAssinatura.save(assinaturaExistente);
        } else {
            throw new RuntimeException("Assinatura não encontrada com o ID: " + id);
        }
    }

    public void deletarAssinatura(Long id) {
        repAssinatura.deleteById(id);
    }

    public Pagamento cadastrarPagamento(Pagamento pagamento) {
        Assinatura assinatura = pagamento.getAssinatura();
        LocalDate fimVigencia = assinatura.getFimVigencia();
        assinatura.setFimVigencia(fimVigencia.plusMonths(1));
        repAssinatura.save(assinatura);
        return repPagamento.save(pagamento);
    }

    public Optional<Pagamento> buscarPagamentoPorId(Long id) {
        return repPagamento.findById(id);
    }

    public Pagamento atualizarPagamento(Long id, Pagamento novosDados) {
        Optional<Pagamento> pagamentoExistenteOpt = repPagamento.findById(id);
    
        if (pagamentoExistenteOpt.isPresent()) {
            Pagamento pagamentoExistente = pagamentoExistenteOpt.get();
            
            pagamentoExistente.setAssinatura(novosDados.getAssinatura());
            pagamentoExistente.setValorPago(novosDados.getValorPago());
            pagamentoExistente.setDataPagamento(novosDados.getDataPagamento());
            
            return repPagamento.save(pagamentoExistente);
        } else {
            throw new RuntimeException("Pagamento não encontrado com o ID: " + id);
        }
    }

    public void deletarPagamento(Long id) {
        repPagamento.deleteById(id);
    }

    public java.util.List<Cliente> listarClientes() {
        return repCliente.findAll();
    }

    public List<Aplicativo> listarApps() {
        return repAplicativo.findAll();
    }

    public Aplicativo atualizarCustoMensal(Long id, float novoCusto) {
        Optional<Aplicativo> aplicativoExistenteOpt = repAplicativo.findById(id);
    
        if (aplicativoExistenteOpt.isPresent()) {
            Aplicativo aplicativoExistente = aplicativoExistenteOpt.get();
            aplicativoExistente.setCustoMensal(novoCusto);
            return repAplicativo.save(aplicativoExistente);
        } else {
            throw new RuntimeException("Aplicativo não encontrado com o ID: " + id);
        }
    }

    public List<Assinatura> buscarAssinaturasPorTipo(String tipo) {
        List<Assinatura> todasAssinaturas = repAssinatura.findAll();

        switch (tipo.toUpperCase()) {
            case "ATIVAS":
                return todasAssinaturas.stream()
                    .filter(assinatura -> assinatura.getFimVigencia().isAfter(LocalDate.now()))
                    .collect(Collectors.toList());
            case "CANCELADAS":
                return todasAssinaturas.stream()
                    .filter(assinatura -> assinatura.getFimVigencia().isBefore(LocalDate.now()))
                    .collect(Collectors.toList());
            case "TODAS":
                return todasAssinaturas;
            default:
                return null;
        }
    }

    public List<Assinatura> getAssinaturasByCliente(Long codCli) {
        return repAssinatura.findByCliente_Codigo(codCli);
    }

    public List<Assinatura> getAssinaturasByCodApp(Long codApp) {
        return repAssinatura.findByAplicativo_Codigo(codApp);
    }

}
