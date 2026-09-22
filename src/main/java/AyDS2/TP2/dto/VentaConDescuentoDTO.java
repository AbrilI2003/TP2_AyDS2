package AyDS2.TP2.dto;

public class VentaConDescuentoDTO {

    private String producto;
    private int cantidad;
    private double precioUnitario;
    private double montoConDescuento;

    public VentaConDescuentoDTO(String producto, int cantidad, double precioUnitario, double montoConDescuento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.montoConDescuento = montoConDescuento;
    }

    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getMontoConDescuento() { return montoConDescuento; }
}