package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.ConsultaModel;
import br.com.smartmed.consultas.repository.ConsultaRepository;
import br.com.smartmed.consultas.rest.dto.ConsultaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

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

}
