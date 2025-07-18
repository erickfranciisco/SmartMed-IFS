package br.com.smartmed.consultas.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultaDTO {
    private long id;
    private LocalDateTime dataHoraConsulta;
    private String status;
    private float valor;
    private String observacoes;
    private int pacienteId;
    private int medicoId;
    private int formaPagamentoId;
    private int convenioId;
    private int recepcionistaId;
}
