package AyDS2.TP2.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import AyDS2.TP2.dto.EstadisticasDTO;
import AyDS2.TP2.dto.VentaDTO;
import AyDS2.TP2.dto.DescuentoResponseDTO;
import AyDS2.TP2.dto.VentaConDescuentoDTO;

@Service
public class VentaService {

    public EstadisticasDTO calcularEstadisticas(List<VentaDTO> ventas) {

        double totalFacturado = ventas.stream()
                .mapToDouble(VentaDTO::getImporte)
                .sum();

        int cantidadVentas = ventas.size();

        double ticketPromedio = totalFacturado / cantidadVentas;

        VentaDTO ventaMayor = ventas.stream()
                .max(Comparator.comparingDouble(VentaDTO::getImporte))
                .orElse(null);

        VentaDTO ventaMenor = ventas.stream()
                .min(Comparator.comparingDouble(VentaDTO::getImporte))
                .orElse(null);

        Map<String, Integer> cantidadPorProducto = new HashMap<>();

        for (VentaDTO venta : ventas) {
            String nombre = venta.getProducto();
            int acumuladoActual = cantidadPorProducto.getOrDefault(nombre, 0);
            cantidadPorProducto.put(nombre, acumuladoActual + venta.getCantidad());
        }

        String productoMasVendido = cantidadPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return new EstadisticasDTO(totalFacturado, cantidadVentas, ticketPromedio,
                ventaMayor, ventaMenor, productoMasVendido);
    }

    public DescuentoResponseDTO aplicarDescuento(List<VentaDTO> ventas, double porcentaje) {

        if (porcentaje < 0 || porcentaje > 100) {
                throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }

        List<VentaConDescuentoDTO> ventasConDescuento = ventas.stream()
            .map(venta -> {
                double montoConDescuento = venta.getImporte() * (1 - porcentaje / 100.0);
                return new VentaConDescuentoDTO(
                        venta.getProducto(),
                        venta.getCantidad(),
                        venta.getPrecioUnitario(),
                        montoConDescuento
                );
            })
            .collect(Collectors.toList());

        double totalConDescuento = ventasConDescuento.stream()
            .mapToDouble(VentaConDescuentoDTO::getMontoConDescuento)
            .sum();

        return new DescuentoResponseDTO(ventasConDescuento, totalConDescuento);
    }
}