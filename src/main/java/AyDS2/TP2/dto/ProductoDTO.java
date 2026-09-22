package AyDS2.TP2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ProductoDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String categoria;

    @Positive(message = "El precio debe ser mayor que 0")
    private double precio;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;

    public ProductoDTO() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}