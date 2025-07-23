package br.com.smartmed.consultas.rest.controller;

import br.com.smartmed.consultas.rest.dto.response.relatorio.RelatorioFaturamentoDTO;
import br.com.smartmed.consultas.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/faturamento")
    public ResponseEntity<RelatorioFaturamentoDTO> relatorioFaturamento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dataFim)
    {

        LocalDateTime paramDataInicio = dataInicio.atTime(0, 0, 0);
        LocalDateTime paramDataFim = dataFim.atTime(23, 59, 59);

        RelatorioFaturamentoDTO relatorioGeralDTO = consultaService.gerarFaturamentoPeriodo(paramDataInicio, paramDataFim);
        return ResponseEntity.ok(relatorioGeralDTO);
    }
}
