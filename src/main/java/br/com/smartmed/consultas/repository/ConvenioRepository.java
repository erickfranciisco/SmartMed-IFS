package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConvenioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConvenioRepository extends JpaRepository<ConvenioModel, Integer> {
    // Id
    @Override
    Optional<ConvenioModel> findById(Integer integer);

    @Override
    List<ConvenioModel> findAllById(Iterable<Integer> integers);

    @Override
    List<ConvenioModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Nome
    List<ConvenioModel> findByNome(String paramNome);

    // Cnpj
    Optional<ConvenioModel> findByCnpj(String paramCnpj);
    boolean existsByCnpj(String paramCnpj);
    void deleteByCnpj(String paramCnpj);

    // Ativo
    List<ConvenioModel> findByAtivo(boolean paramAtivo);

    // Ativo + Cnpj
    Optional<ConvenioModel> findByAtivoAndCnpj(boolean paramAtivo, String paramCnpj);
}
