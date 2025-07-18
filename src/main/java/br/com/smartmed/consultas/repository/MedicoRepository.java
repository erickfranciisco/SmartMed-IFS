package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.MedicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoModel, Integer> {
    // Id
    @Override
    Optional<MedicoModel> findById(Integer integer);

    @Override
    List<MedicoModel> findAllById(Iterable<Integer> integers);

    @Override
    List<MedicoModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Nome
    List<MedicoModel> findByNome(String paraNome);
    Optional<MedicoModel> findFirstByNomeContaining(String paramNome);

    // Crm
    Optional<MedicoModel> findByCrm(String paramCrm);
    boolean existsByCrm(String paramCrm);
    void deleteByCrm(String paramCrm);

    // ativo
    List<MedicoModel> findByAtivo(boolean paramAtivo);

    // Ativo + Crm
    Optional<MedicoModel> findByAtivoAndCrm(boolean paramAtivo, String paramCrm);

    /*
    Especialiadade
        List<MedicoModel> find

    Especialdiade + Crm
        Optional<MedicoModel> find
     */
}
