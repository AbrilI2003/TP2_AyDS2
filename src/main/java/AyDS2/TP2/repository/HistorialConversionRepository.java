package AyDS2.TP2.repository;

import AyDS2.TP2.entity.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Integer> {

    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
            String monedaOrigen, String monedaDestino);
}