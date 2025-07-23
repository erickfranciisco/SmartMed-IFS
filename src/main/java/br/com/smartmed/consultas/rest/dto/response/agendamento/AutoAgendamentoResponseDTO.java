package br.com.smartmed.consultas.rest.dto.response.agendamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AutoAgendamentoResponseDTO {
    private Long id;
    private LocalDateTime dataHoraConsulta;
    private String nomeMedico;
    private String nomePaciente;
    private double valor;
}
