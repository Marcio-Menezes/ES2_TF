package com.pucrs.microsservicos.ServicoCadastramento.Interface;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.models.*;
import com.pucrs.microsservicos.ServicoCadastramento.Dominio.dto.*;

import com.pucrs.microsservicos.ServicoCadastramento.Dominio.services.ServicoCadastramento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/servcad")
public class ControllerServcad {
    
    @Autowired
    private ServicoCadastramento servicoCadastramento;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return servicoCadastramento.listarClientes(); 
    }

    @GetMapping("/aplicativos")
    public List<Aplicativo> listarApps() {
        return servicoCadastramento.listarApps();
    }

    @PostMapping("/assinaturas")
    public ResponseEntity<Assinatura> criaAssinatura(@RequestBody Assinatura novaAssinatura)  {
        Assinatura assinatura = servicoCadastramento.cadastrarAssinatura(novaAssinatura.getCliente().getCodigo(), novaAssinatura.getAplicativo().getCodigo());
        return ResponseEntity.ok(assinatura);
    }

    @PatchMapping("/aplicativos/{idAplicativo}")
    public ResponseEntity<Aplicativo> atualizarCustoMensal(@PathVariable Long idAplicativo, @RequestBody Map<String, Float> custo) {
        float novoCusto = custo.get("novoCusto");
        Aplicativo aplicativoAtualizado = servicoCadastramento.atualizarCustoMensal(idAplicativo, novoCusto);
        return ResponseEntity.ok(aplicativoAtualizado);
    }

    @GetMapping("/assinaturas/{tipo}")
    public ResponseEntity<List<AssinaturaDTO>> buscarAssinaturasPorTipo(@PathVariable String tipo) {
        List<Assinatura> assinaturas = servicoCadastramento.buscarAssinaturasPorTipo(tipo);
        List<AssinaturaDTO> assinaturaDTOs = assinaturas.stream().map(Assinatura::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(assinaturaDTOs);
    }

    @GetMapping("/asscli/{codcli}")
    public List<Assinatura> getAssinaturasByCliente(@PathVariable Long codcli) {
        return servicoCadastramento.getAssinaturasByCliente(codcli);
    }

    @GetMapping("/assapp/{codapp}")
    public List<Assinatura> getAssinaturasByAPlicativo(@PathVariable Long codapp) {
        return servicoCadastramento.getAssinaturasByCodApp(codapp);
    }
}
