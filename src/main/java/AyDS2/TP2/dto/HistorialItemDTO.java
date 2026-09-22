package AyDS2.TP2.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HistorialItemDTO {

    private LocalDateTime fecha;
    private BigDecimal tasaCambio;

    public HistorialItemDTO(LocalDateTime fecha, BigDecimal tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public LocalDateTime getFecha() { return fecha; }
    public BigDecimal getTasaCambio() { return tasaCambio; }
}