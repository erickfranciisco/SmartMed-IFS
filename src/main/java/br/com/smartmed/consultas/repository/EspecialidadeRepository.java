package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.EspecialidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeModel, Integer> {
    // Id
    @Override
    Optional<EspecialidadeModel> findById(Integer integer);

    @Override
    List<EspecialidadeModel> findAllById(Iterable<Integer> integers);

    @Override
    List<EspecialidadeModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Nome
    List<EspecialidadeModel> findByNome(String paramNome);

    boolean existsByNome(String paramNome);

}
