package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Integer> {
    // Id
    @Override
    Optional<FormaPagamentoModel> findById(Integer integer);

    @Override
    List<FormaPagamentoModel> findAllById(Iterable<Integer> integers);

    @Override
    List<FormaPagamentoModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Descrição
    List<FormaPagamentoModel> findByDescricao(String paramDescricao);
    boolean existsByDescricao(String paramDescricao);
}
