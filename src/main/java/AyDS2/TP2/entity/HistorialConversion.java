package AyDS2.TP2.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_conversiones")
public class HistorialConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "moneda_origen", nullable = false, length = 3)
    private String monedaOrigen;

    @Column(name = "moneda_destino", nullable = false, length = 3)
    private String monedaDestino;

    @Column(name = "monto_solicitado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoSolicitado;

    @Column(name = "monto_convertido", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoConvertido;

    @Column(name = "tasa_cambio", nullable = false, precision = 15, scale = 6)
    private BigDecimal tasaCambio;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;

    public HistorialConversion() {}

    public HistorialConversion(String monedaOrigen, String monedaDestino,
                                BigDecimal montoSolicitado, BigDecimal montoConvertido,
                                BigDecimal tasaCambio, LocalDateTime fechaConsulta) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.montoSolicitado = montoSolicitado;
        this.montoConvertido = montoConvertido;
        this.tasaCambio = tasaCambio;
        this.fechaConsulta = fechaConsulta;
    }

    public Integer getId() { return id; }
    public String getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(String m) { this.monedaOrigen = m; }
    public String getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(String m) { this.monedaDestino = m; }
    public BigDecimal getMontoSolicitado() { return montoSolicitado; }
    public void setMontoSolicitado(BigDecimal m) { this.montoSolicitado = m; }
    public BigDecimal getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(BigDecimal m) { this.montoConvertido = m; }
    public BigDecimal getTasaCambio() { return tasaCambio; }
    public void setTasaCambio(BigDecimal t) { this.tasaCambio = t; }
    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime f) { this.fechaConsulta = f; }
}