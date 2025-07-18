package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.RecepcionistaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecepcionistaRepository extends JpaRepository<RecepcionistaModel, Integer> {
    @Override
    Optional<RecepcionistaModel> findById(Integer integer);

    @Override
    List<RecepcionistaModel> findAllById(Iterable<Integer> integers);

    @Override
    List<RecepcionistaModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Nome
    List<RecepcionistaModel> findByNome(String paramNome);

    // Cpf
    Optional<RecepcionistaModel> findByCpf(String paramCpf);
    boolean existsByCpf(String paramCpf);
    void deleteByCpf(String Cpf);

    // Ativo
    List<RecepcionistaModel> findByAtivo(boolean paramAtivo);

    // Ativo + Cpf
    Optional<RecepcionistaModel> findByAtivoAndCpf(boolean paramAtivo, String paramCpf);
}
