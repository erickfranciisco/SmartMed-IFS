package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.*;
import br.com.smartmed.consultas.repository.*;
import br.com.smartmed.consultas.rest.dto.request.ConsultaDTO;
import br.com.smartmed.consultas.rest.dto.request.agendamento.AutoAgendamentoRequestDTO;
import br.com.smartmed.consultas.rest.dto.response.agendamento.AutoAgendamentoResponseDTO;
import br.com.smartmed.consultas.rest.dto.response.relatorio.FaturamentoConvenioDTO;
import br.com.smartmed.consultas.rest.dto.response.relatorio.FaturamentoFormaPagamentoDTO;
import br.com.smartmed.consultas.rest.dto.response.relatorio.RelatorioFaturamentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ConvenioRepository convenioRepository;

    @Transactional(readOnly = true)
    public ConsultaDTO obterPorId(long id) {
        ConsultaModel consulta = consultaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Consulta com ID " + id + " não encontrada."));
        return consulta.toDTO();
    }

    @Transactional(readOnly = true)
    public List<ConsultaDTO> obterTodos() {
        List<ConsultaModel> consultas  = consultaRepository.findAll();
        return consultas.stream().map(consulta -> consulta.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public ConsultaDTO salvar(ConsultaModel novaConsulta) {
        try {
            if (consultaRepository.existsById(novaConsulta.getId())) {
                throw new ConstraintException("Já existe uma consulta com esse ID " + novaConsulta.getId() +  " na base de dados!");
            }

            return consultaRepository.save(novaConsulta).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar a consulta!");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar a consulta.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar a consulta. Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar a consulta. Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public ConsultaDTO atualizar(ConsultaModel consultaExistente) {
        try {
            if (!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("A consulta não existe na base de dados.");
            }

            return consultaRepository.save(consultaExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar a consulta!");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar a consulta: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar a consulta. Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar a consulta. Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar a consulta. Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(ConsultaModel consultaExistente) {
        try {
            if (!consultaRepository.existsById(consultaExistente.getId())) {
                throw new ConstraintException("Consulta inexistente na base de dados.");
            }

            consultaRepository.delete(consultaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar a consulta!");
        } catch (ConstraintException e) {

            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar a consulta: Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar a consulta. Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar. Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar a consulta. Não encontrado no banco de dados!");
        }
    }

    @Transactional(readOnly = true)
    public RelatorioFaturamentoDTO gerarFaturamentoPeriodo(LocalDateTime paramInicio, LocalDateTime paramFim) {

        // Cálculo do Faturamento Total
        double valorTotal = consultaRepository.calcularFaturamentoTotalPeriodo("REALIZADA", paramInicio, paramFim)
                .orElseThrow(() -> new ObjectNotFoundException("Não foi possível calcular o faturamento total no prazo estabelecido"));

        // FormaPagamento
        List<Object[]> formaPag = consultaRepository.calcularFaturamentoFormaPagPeriodo("REALIZADA", paramInicio, paramFim);
        List<FaturamentoFormaPagamentoDTO> porFormaPagamento = formaPag.stream()
                .map(formaPagamentoObj -> new FaturamentoFormaPagamentoDTO((String) formaPagamentoObj[0], (double) formaPagamentoObj[1]))
                .collect(Collectors.toList());

        // Convênio
        List<Object[]> convenio = consultaRepository.calcularFaturamentoConvenioPeriodo("REALIZADA", paramInicio, paramFim);
        List<FaturamentoConvenioDTO> porConvenio = convenio.stream()
                .map(convenioObj -> new FaturamentoConvenioDTO((String) convenioObj[0], (double) convenioObj[1]))
                .collect(Collectors.toList());

        return new RelatorioFaturamentoDTO(valorTotal, porFormaPagamento, porConvenio);
    }

    @Transactional
    public AutoAgendamentoResponseDTO autoAgendamento(AutoAgendamentoRequestDTO agendamentoRequest) {
        List<MedicoModel> medicos = medicoRepository.buscarPorEspecialidadeOuMedico(agendamentoRequest.getEspecialidadeId(), agendamentoRequest.getMedicoId());

        if (medicos.isEmpty()) {
            throw new ObjectNotFoundException("Nenhum médico disponível, ou encontrado.");
        } else {
            for (MedicoModel medico : medicos) {
                if (buscarPrimeiroHorarioLivre(medico.getId(), agendamentoRequest.getDataHoraInicial(), agendamentoRequest.getDuracaoConsultaMinutos()) != null) {
                    PacienteModel paciente = pacienteRepository.findById(agendamentoRequest.getPacienteId())
                            .orElseThrow(() -> new ObjectNotFoundException("Paciente não encontrado."));


                    ConsultaModel novaConsulta = new ConsultaModel();
                    novaConsulta.setDataHoraConsulta(buscarPrimeiroHorarioLivre(medico.getId(), agendamentoRequest.getDataHoraInicial(), agendamentoRequest.getDuracaoConsultaMinutos()));
                    novaConsulta.setMedicoId(medico.getId());
                    novaConsulta.setStatus("AGENDADA");
                    novaConsulta.setPacienteId(agendamentoRequest.getPacienteId());
                    novaConsulta.setTempoMedioConsultaMinutos(agendamentoRequest.getDuracaoConsultaMinutos());
                    novaConsulta.setValor(medico.getValorConsultaReferencia() / 2);
                    salvar(novaConsulta);
                    consultaRepository.save(novaConsulta);

                    if (agendamentoRequest.getFormaPagamentoId() != 0) {
                        formaPagamentoRepository.findById(agendamentoRequest.getFormaPagamentoId())
                                .orElseThrow(() -> new ObjectNotFoundException("Infelizmente a FORMA DE PAGAMETO não encontrado!"));
                        novaConsulta.setFormaPagamentoId(agendamentoRequest.getFormaPagamentoId());
                    }

                    if (agendamentoRequest.getConvenioId() != 0) {
                        convenioRepository.findById(agendamentoRequest.getConvenioId())
                                .orElseThrow(() -> new ObjectNotFoundException("Infelizmente o CONVÊNIO não encontrado!"));
                        novaConsulta.setConvenioId(agendamentoRequest.getConvenioId());
                    }

                    consultaRepository.save(novaConsulta);

                    return new AutoAgendamentoResponseDTO(
                            novaConsulta.getId(),
                            novaConsulta.getDataHoraConsulta(),
                            medico.getNome(),
                            paciente.getNome(),
                            novaConsulta.getValor()
                    );
                }
            }
        }
        throw new RuntimeException("Não há horários disponíveis nos parâmetros informados.");
    }

    private LocalDateTime buscarPrimeiroHorarioLivre(Integer medicoId, LocalDateTime dataInicio, int duracaoMinutos) {
        LocalDateTime fimExpediente = dataInicio.withHour(18).withMinute(0);
        List<ConsultaModel> consultas = consultaRepository.findConsultasMedicoDia(medicoId, dataInicio, fimExpediente);
        consultas.sort(Comparator.comparing(ConsultaModel::getDataHoraConsulta));
        LocalDateTime horarioConsulta = dataInicio;

        for (ConsultaModel consuta : consultas) {
            LocalDateTime inicioConsulta = consuta.getDataHoraConsulta();
            LocalDateTime fimConsulta = inicioConsulta.plusMinutes(consuta.getTempoMedioConsultaMinutos());

            if (horarioConsulta.isBefore(fimConsulta) &&
                    horarioConsulta.plusMinutes(duracaoMinutos).isAfter(inicioConsulta)) {
                horarioConsulta = fimConsulta;
            }
        }

        if (horarioConsulta.plusMinutes(duracaoMinutos).isAfter(fimExpediente)) {
            return null;
        }

        return horarioConsulta;
    }


}