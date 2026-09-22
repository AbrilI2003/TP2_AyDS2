package AyDS2.TP2.dto;

import java.util.List;

public class DescuentoResponseDTO {

    private List<VentaConDescuentoDTO> ventas;
    private double totalConDescuento;

    public DescuentoResponseDTO(List<VentaConDescuentoDTO> ventas, double totalConDescuento) {
        this.ventas = ventas;
        this.totalConDescuento = totalConDescuento;
    }

    public List<VentaConDescuentoDTO> getVentas() { return ventas; }
    public double getTotalConDescuento() { return totalConDescuento; }
}