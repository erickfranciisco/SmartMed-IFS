package br.com.smartmed.consultas.rest.dto.response.relatorio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaturamentoConvenioDTO {
    private String convenio;
    private double valor;
}
