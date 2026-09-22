package AyDS2.TP2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class VentaDTO {

    @NotBlank(message = "El producto no puede estar vacío")
    private String producto;

    @Positive(message = "La cantidad debe ser un entero positivo")
    private int cantidad;

    @Positive(message = "El precio unitario debe ser mayor a 0")
    private double precioUnitario;

    public VentaDTO() {
    }

    public VentaDTO(String producto, int cantidad, double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getImporte() {
        return cantidad * precioUnitario;
    }
}