package AyDS2.TP2.dto;

public class EstadisticasDTO {

    @SuppressWarnings("FieldMayBeFinal")
    private double totalFacturado;
    @SuppressWarnings("FieldMayBeFinal")
    private int cantidadVentas;
    @SuppressWarnings("FieldMayBeFinal")
    private double ticketPromedio;
    @SuppressWarnings("FieldMayBeFinal")
    private VentaDTO ventaMayor;
    @SuppressWarnings("FieldMayBeFinal")
    private VentaDTO ventaMenor;
    @SuppressWarnings("FieldMayBeFinal")
    private String productoMasVendido;

    public EstadisticasDTO(double totalFacturado, int cantidadVentas, double ticketPromedio,
                           VentaDTO ventaMayor, VentaDTO ventaMenor, String productoMasVendido) {
        this.totalFacturado = totalFacturado;
        this.cantidadVentas = cantidadVentas;
        this.ticketPromedio = ticketPromedio;
        this.ventaMayor = ventaMayor;
        this.ventaMenor = ventaMenor;
        this.productoMasVendido = productoMasVendido;
    }

    public double getTotalFacturado() { return totalFacturado; }
    public int getCantidadVentas() { return cantidadVentas; }
    public double getTicketPromedio() { return ticketPromedio; }
    public VentaDTO getVentaMayor() { return ventaMayor; }
    public VentaDTO getVentaMenor() { return ventaMenor; }
    public String getProductoMasVendido() { return productoMasVendido; }
}