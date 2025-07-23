package br.com.smartmed.consultas.rest.dto.request.agendamento;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoAgendamentoRequestDTO {
    private int pacienteId;
    private int especialidadeId;
    private int medicoId;
    private LocalDateTime dataHoraInicial;
    private int duracaoConsultaMinutos;
    private int convenioId;
    private int formaPagamentoId;
}
