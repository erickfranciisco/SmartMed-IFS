package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.PacienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteModel, Integer> {
    // Id
    @Override
    Optional<PacienteModel> findById(Integer integer);

    @Override
    List<PacienteModel> findAllById(Iterable<Integer> integers);

    @Override
    List<PacienteModel> findAll();

    @Override
    boolean existsById(Integer integer);

    @Override
    void deleteById(Integer integer);

    // Nome
    List<PacienteModel> findByNome(String paramNome);
    Optional<PacienteModel> findFirstByNomeContaining(String paramNome);

    // Cpf
    Optional<PacienteModel> findByCpf(String paramCpf);
    boolean existsByCpf(String paramCpf);
    void deleteByCpf(String paramCpf);

    // Email
    Optional<PacienteModel> findByEmail(String paramEmail);
    boolean existsByEmail(String paramEmail);
}
