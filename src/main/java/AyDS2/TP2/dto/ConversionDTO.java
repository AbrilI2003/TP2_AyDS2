package AyDS2.TP2.dto;

public class ConversionDTO {

    private double montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private double tasaCambio;
    private double montoConvertido;
    private String fecha;

    public ConversionDTO(double montoOriginal, String monedaOrigen, String monedaDestino,
                          double tasaCambio, double montoConvertido, String fecha) {
        this.montoOriginal = montoOriginal;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }

    public double getMontoOriginal() { return montoOriginal; }
    public String getMonedaOrigen() { return monedaOrigen; }
    public String getMonedaDestino() { return monedaDestino; }
    public double getTasaCambio() { return tasaCambio; }
    public double getMontoConvertido() { return montoConvertido; }
    public String getFecha() { return fecha; }
}