package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel, Long> {
    @Override
    Optional<ConsultaModel> findById(Long aLong);

    @Override
    List<ConsultaModel> findAllById(Iterable<Long> longs);

    @Override
    List<ConsultaModel> findAll();

    @Override
    boolean existsById(Long aLong);

    @Override
    void deleteById(Long aLong);

    List<ConsultaModel> findByStatusAndDataHoraConsultaBetween(String paramStatus, LocalDateTime paramDataInicio, LocalDateTime paramDataFim);
}
