package br.com.smartmed.consultas.repository;

import br.com.smartmed.consultas.model.ConsultaModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    /*
    ========================
    RELATÓRIO DE FATURAMENTO
    ========================
     */
    @Query(value = """
            SELECT SUM(c.valor)
            FROM ConsultaModel c
            WHERE c.status = :paramStatus
            AND c.dataHoraConsulta BETWEEN :paramInicio AND :paramFim
            """)
    Optional<Double> calcularFaturamentoTotalPeriodo(@Param("paramStatus") String paramStatus,
                                                     @Param("paramInicio") LocalDateTime paramInicio,
                                                     @Param("paramFim") LocalDateTime paramFim);

    @Query(value = """
            SELECT f.descricao, SUM(c.valor)
            FROM ConsultaModel c
            JOIN FormaPagamentoModel f ON (f.id = c.formaPagamentoId)
            WHERE c.status = :paramStatus
            AND c.dataHoraConsulta BETWEEN :paramInicio AND :paramFim
            GROUP BY f.descricao
            """)
    List<Object[]> calcularFaturamentoFormaPagPeriodo(@Param("paramStatus") String paramStatus,
                                                      @Param("paramInicio") LocalDateTime paramInicio,
                                                      @Param("paramFim") LocalDateTime paramFim);

    @Query(value = """
            SELECT cv.nome, SUM(c.valor)
            FROM ConsultaModel c
            JOIN ConvenioModel cv ON (cv.id = c.convenioId)
            WHERE c.status = :paramStatus
            AND c.dataHoraConsulta BETWEEN :paramInicio AND :paramFim
            GROUP BY cv.nome
            """)
    List<Object[]> calcularFaturamentoConvenioPeriodo(@Param("paramStatus") String paramStatus,
                                                      @Param("paramInicio") LocalDateTime paramInicio,
                                                      @Param("paramFim") LocalDateTime paramFim);


    /*
    ==================================
    AGENDAMENTO AUTOMÁTICO DE CONSULTA
    ==================================
     */
/*
    Query:. findConsultasMedicoDia
    Objetivo: Buscar todas as CONSULTAS MARCADAS de um respectivo MÉDICO em determinado DIA/HORA.

    @param paramMedicoId refere-se ao ID do MÉDICO, o qual suas consultas serão retornadas.
    @param paramDataHoraInicial refere-se ao DIA/HORA inicial da busca
    @param paramDataHoraFim refere-se ao DIA/HORA final da busca, mais precisamente no final do expediente da clínica

                                --------------------TERMINAR DEPOIS DE FAZER OS COMENTÁRIOS--------------------
*/
    @Query("""
    SELECT c
    FROM ConsultaModel c
    WHERE c.medicoId = :paramMedicoId
    AND c.dataHoraConsulta >= :paramDataHoraInicial
    AND c.dataHoraConsulta < :paramDataHoraFim
    ORDER BY c.dataHoraConsulta ASC
""")
    List<ConsultaModel> findConsultasMedicoDia(Integer paramMedicoId, LocalDateTime paramDataHoraInicial, LocalDateTime paramDataHoraFim);


}
