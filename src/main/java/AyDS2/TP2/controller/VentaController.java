package AyDS2.TP2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AyDS2.TP2.dto.ApiResponse;
import AyDS2.TP2.dto.DescuentoResponseDTO;
import AyDS2.TP2.dto.EstadisticasDTO;
import AyDS2.TP2.dto.VentaDTO;
import AyDS2.TP2.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(summary = "Calcula estadísticas de un lote de ventas")
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticasDTO>> calcularEstadisticas(
            @RequestBody @Valid @NotEmpty(message = "La lista de ventas no puede estar vacía") List<@Valid VentaDTO> ventas) {

        EstadisticasDTO estadisticas = ventaService.calcularEstadisticas(ventas);

        ApiResponse<EstadisticasDTO> respuesta = new ApiResponse<>(
                200,
                "Operacion realizada con exito",
                estadisticas
        );

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Aplica un descuento a un lote de ventas")
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponse<DescuentoResponseDTO>> aplicarDescuento(
            @RequestBody @Valid @NotEmpty(message = "La lista de ventas no puede estar vacía") List<@Valid VentaDTO> ventas,
            @RequestParam double porcentaje) {

        DescuentoResponseDTO resultado = ventaService.aplicarDescuento(ventas, porcentaje);

        ApiResponse<DescuentoResponseDTO> respuesta = new ApiResponse<>(
                200,
                "Operacion realizada con exito",
                resultado
        );

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
