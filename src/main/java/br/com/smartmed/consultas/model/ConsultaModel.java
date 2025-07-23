package br.com.smartmed.consultas.model;

import br.com.smartmed.consultas.rest.dto.request.ConsultaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consulta")
public class ConsultaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "dataHoraConsulta", nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(name = "status", length = 16, nullable = false)
    private String status;

    @Column(name = "valor", nullable = false)
    private float valor;

    @Column(name = "tempoMedioConsultaMinutos", nullable = false)
    private int tempoMedioConsultaMinutos;

    @Column(name = "observacoes", length = 1024)
    private String observacoes;

    @Column(name = "pacienteId", nullable = false)
    private int pacienteId;

    @Column(name = "medicoId", nullable = false)
    private int medicoId;

    @Column(name = "formaPagamentoId")
    private int formaPagamentoId;

    @Column(name = "convenioId")
    private int convenioId;

    @Column(name = "recepcionistaId", nullable = false)
    private int recepcionistaId;

    public ConsultaDTO toDTO() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConsultaDTO.class);
    }
}
