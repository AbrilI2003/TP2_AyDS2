package AyDS2.TP2.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoResponseDTO {

    private Integer pedidoId;
    private String cliente;
    private LocalDate fecha;
    private String estado;
    private BigDecimal totalPedido;
    private List<ProductoPedidoDTO> productos;

    public PedidoResponseDTO(Integer pedidoId, String cliente, LocalDate fecha, String estado,
                              BigDecimal totalPedido, List<ProductoPedidoDTO> productos) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.productos = productos;
    }

    public Integer getPedidoId() { return pedidoId; }
    public String getCliente() { return cliente; }
    public LocalDate getFecha() { return fecha; }
    public String getEstado() { return estado; }
    public BigDecimal getTotalPedido() { return totalPedido; }
    public List<ProductoPedidoDTO> getProductos() { return productos; }
}