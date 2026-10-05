package AyDS2.TP2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import AyDS2.TP2.entity.HistorialConversion;

@Repository
public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Integer> {

    @Query("SELECT h FROM HistorialConversion h " +
           "WHERE h.monedaOrigen = :origen " +
           "AND h.monedaDestino = :destino " +
           "ORDER BY h.fechaConsulta DESC")
    List<HistorialConversion> buscarHistorial(
            @Param("origen") String origen,
            @Param("destino") String destino);
}