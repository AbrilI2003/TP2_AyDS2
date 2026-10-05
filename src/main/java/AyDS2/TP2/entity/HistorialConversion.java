package AyDS2.TP2.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
                                    
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

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "monto_convertido", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoConvertido;

    @Column(name = "tasa", nullable = false, precision = 15, scale = 6)
    private BigDecimal tasa;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;

    public HistorialConversion() {}

    public HistorialConversion(String monedaOrigen, String monedaDestino,
                                BigDecimal monto, BigDecimal montoConvertido,
                                BigDecimal tasa, LocalDateTime fechaConsulta) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.monto = monto;
        this.montoConvertido = montoConvertido;
        this.tasa = tasa;
        this.fechaConsulta = fechaConsulta;
    }

    public Integer getId() { return id; }
    public String getMonedaOrigen() { return monedaOrigen; }
    public void setMonedaOrigen(String m) { this.monedaOrigen = m; }
    public String getMonedaDestino() { return monedaDestino; }
    public void setMonedaDestino(String m) { this.monedaDestino = m; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal m) { this.monto = m; }
    public BigDecimal getMontoConvertido() { return montoConvertido; }
    public void setMontoConvertido(BigDecimal m) { this.montoConvertido = m; }
    public BigDecimal getTasa() { return tasa; }
    public void setTasa(BigDecimal t) { this.tasa = t; }
    public LocalDateTime getFechaConsulta() { return fechaConsulta; }
    public void setFechaConsulta(LocalDateTime f) { this.fechaConsulta = f; }
}