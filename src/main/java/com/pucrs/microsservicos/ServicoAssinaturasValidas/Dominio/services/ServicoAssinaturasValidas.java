package com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.services;

import com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.models.AssinaturaValida;
import com.pucrs.microsservicos.ServicoAssinaturasValidas.Dominio.repositories.IRepAssinaturaValida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicoAssinaturasValidas {

    @Autowired
    private IRepAssinaturaValida repAssinaturaValida;

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable("assinaturasValidas")
    public boolean isAssinaturaValida(Long codAss) {
        Optional<AssinaturaValida> assinatura = repAssinaturaValida.findById(codAss);
        if (assinatura.isPresent() && assinatura.get().getDataFimVigencia().isAfter(LocalDate.now())) {
            return true;
        } else {
            // Consultar o ServicoCadastramento
            String url = "http://localhost:8080/servcad/assinaturas/ATIVAS";
            List<Map<String, Object>> assinaturasAtivas = restTemplate.getForObject(url, List.class);
            if (assinaturasAtivas != null) {
                for (Map<String, Object> assinaturaMap : assinaturasAtivas) {
                    Long codigoAssinatura = ((Number) assinaturaMap.get("codigo")).longValue();
                    if (codigoAssinatura.equals(codAss)) {
                        String dataFimStr = (String) assinaturaMap.get("fimVigencia");
                        if (dataFimStr != null) {
                            LocalDate dataFim = LocalDate.parse(dataFimStr, DateTimeFormatter.ISO_DATE);
                            // Atualizar a cache
                            AssinaturaValida novaAssinaturaValida = new AssinaturaValida();
                            novaAssinaturaValida.setCodAss(codAss);
                            novaAssinaturaValida.setDataFimVigencia(dataFim);
                            repAssinaturaValida.save(novaAssinaturaValida);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
