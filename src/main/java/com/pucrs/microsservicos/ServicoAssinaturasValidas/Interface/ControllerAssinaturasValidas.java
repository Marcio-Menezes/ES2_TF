package com.pucrs.microsservicos.ServicoAssinaturasValidas.Interface;

import com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.services.ServicoAssinaturasValidas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assinvalidas")
public class ControllerAssinaturasValidas {

    @Autowired
    private ServicoAssinaturasValidas servicoAssinaturasValidas;

    @GetMapping("/{codAss}")
    public ResponseEntity<Boolean> isAssinaturaValida(@PathVariable Long codAss) {
        boolean isValida = servicoAssinaturasValidas.isAssinaturaValida(codAss);
        return ResponseEntity.ok(isValida);
    }
}
