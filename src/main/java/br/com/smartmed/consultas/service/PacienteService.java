package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.PacienteModel;
import br.com.smartmed.consultas.repository.PacienteRepository;
import br.com.smartmed.consultas.rest.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public PacienteDTO obterPorId(int id) {
        PacienteModel paciente = pacienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Paciente com ID " + id + " não encontrado."));
        return paciente.toDTO();
    }

    @Transactional(readOnly = true)
    public List<PacienteDTO> obterTodos() {
        List<PacienteModel> pacientes = pacienteRepository.findAll();
        return pacientes.stream().map(paciente -> paciente.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public PacienteDTO salvar(PacienteModel novoPaciente) {

        try {

            if (pacienteRepository.existsByCpf(novoPaciente.getCpf())) {
                throw new ConstraintException("Já existe um paciente com esse CPF " + novoPaciente.getCpf() + " na base de dados!");
            }

            return pacienteRepository.save(novoPaciente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o paciente " + novoPaciente.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o paciente " + novoPaciente.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o paciente " + novoPaciente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o paciente " + novoPaciente.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public PacienteDTO atualizar(PacienteModel pacienteExistente) {
        try {
            if (!pacienteRepository.existsByCpf(pacienteExistente.getCpf())) {
                throw new ConstraintException("Paciente com o CPF " + pacienteExistente.getCpf() + " não encontrado na base de dados.");
            }

            return pacienteRepository.save(pacienteExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o paciente " + pacienteExistente.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o paciente " + pacienteExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o paciente " + pacienteExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o paciente " + pacienteExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o paciente " + pacienteExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(PacienteModel pacienteExistente) {
        try {
            if (!pacienteRepository.existsById(pacienteExistente.getId())) {
                throw new ConstraintException("Paciente inexistente na base de dados.");
            }

            pacienteRepository.delete(pacienteExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o paciente " + pacienteExistente.getNome() + " !");
        } catch (ConstraintException e) {

            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o paciente " + pacienteExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o paciente " + pacienteExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + pacienteExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o paciente " + pacienteExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }


}