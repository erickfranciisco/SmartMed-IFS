package br.com.smartmed.consultas.rest.dto.response.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaturamentoFormaPagamentoDTO {
    private String formaPagamento;
    private double valor;
}
