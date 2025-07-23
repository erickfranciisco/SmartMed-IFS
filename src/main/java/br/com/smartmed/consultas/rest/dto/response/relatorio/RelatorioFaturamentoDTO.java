package br.com.smartmed.consultas.rest.dto.response.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioFaturamentoDTO {
    private double valorTotal;
    private List<FaturamentoFormaPagamentoDTO> porFormaPagamento;
    private List<FaturamentoConvenioDTO> porConvenio;
}
