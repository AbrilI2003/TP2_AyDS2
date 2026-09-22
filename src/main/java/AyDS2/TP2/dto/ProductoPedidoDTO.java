package AyDS2.TP2.dto;

import java.math.BigDecimal;

public class ProductoPedidoDTO {

    private String nombre;
    private String categoria;
    private int cantidad;
    private BigDecimal subtotal;

    public ProductoPedidoDTO(String nombre, String categoria, int cantidad, BigDecimal subtotal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public int getCantidad() { return cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
}