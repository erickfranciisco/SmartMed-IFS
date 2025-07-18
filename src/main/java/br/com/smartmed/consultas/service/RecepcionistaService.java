package br.com.smartmed.consultas.service;

import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.RecepcionistaModel;
import br.com.smartmed.consultas.repository.RecepcionistaRepository;
import br.com.smartmed.consultas.rest.dto.RecepcionistaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Transactional(readOnly = true)
    public RecepcionistaDTO obterPorId(int id) {
        RecepcionistaModel recepcionista = recepcionistaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Recepcionista com ID " + id + " não encontrado."));
        return recepcionista.toDTO();
    }

    @Transactional(readOnly = true)
    public List<RecepcionistaDTO> obterTodos() {
        List<RecepcionistaModel> recepcionistas = recepcionistaRepository.findAll();
        return recepcionistas.stream().map(recepcionista -> recepcionista.toDTO()).collect(Collectors.toList());
    }

    @Transactional
    public RecepcionistaDTO salvar(RecepcionistaModel novoRecepcionista) {
        try {
            if (recepcionistaRepository.existsByCpf(novoRecepcionista.getCpf())) {
                throw new ConstraintException("Já existe um recepcionista com esse CPF " + novoRecepcionista.getNome() + "cadastrado.");
            }

            return recepcionistaRepository.save(novoRecepcionista).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o recepcionista " + novoRecepcionista.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o recepcionista " + novoRecepcionista.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public RecepcionistaDTO atualizar(RecepcionistaModel recepcionistaExistente) {
        try {
            if (!recepcionistaRepository.existsByCpf(recepcionistaExistente.getCpf())) {
                throw new ConstraintException("Recepcionista com o CPF: " + recepcionistaExistente.getCpf() + " não existe na base de dados.");
            }

            return recepcionistaRepository.save(recepcionistaExistente).toDTO();

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao atualizar o recepcionista " + recepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível atualizar o recepcionista " + recepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }

    @Transactional
    public void deletar(RecepcionistaModel recepcionistaExistente) {
        try {
            if (!recepcionistaRepository.existsById(recepcionistaExistente.getId())) {
                throw new ConstraintException("Recepcionista inexistente na base de dados.");
            }

            recepcionistaRepository.delete(recepcionistaExistente);

        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + " !");
        } catch (ConstraintException e) {
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o recepcionista " + recepcionistaExistente.getNome() + ": Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível atualizar o deletar " + recepcionistaExistente.getNome() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o recepcionista " + recepcionistaExistente.getNome() + ". Não encontrado no banco de dados!");
        }
    }
}
