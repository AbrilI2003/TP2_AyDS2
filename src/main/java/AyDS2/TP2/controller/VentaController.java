package AyDS2.TP2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AyDS2.TP2.dto.ApiResponseDTO;
import AyDS2.TP2.dto.DescuentoResponseDTO;
import AyDS2.TP2.dto.EstadisticasDTO;
import AyDS2.TP2.dto.VentaDTO;
import AyDS2.TP2.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }
    /*---------------------------------------------------------------------------------------------------- */
    @Operation(
            summary = "Calcula estadísticas de un lote de ventas",
            description = "Recibe una lista de ventas y devuelve totales, promedios y el producto más vendido."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estadísticas calculadas correctamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "La lista viene vacía o alguna venta es inválida",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponseDTO<EstadisticasDTO>> calcularEstadisticas(
            @RequestBody @Valid @NotEmpty(message = "La lista de ventas no puede estar vacía") List<@Valid VentaDTO> ventas) {

        EstadisticasDTO estadisticas = ventaService.calcularEstadisticas(ventas);

        ApiResponseDTO<EstadisticasDTO> respuesta = new ApiResponseDTO<>(
                200, "Operacion realizada con exito", estadisticas);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
    /*---------------------------------------------------------------------------------------------------- */
    @Operation(
            summary = "Aplica un descuento a un lote de ventas",
            description = "Calcula el monto con descuento por venta y el total general con descuento aplicado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Descuento aplicado correctamente",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Porcentaje fuera de rango (0-100) o ventas inválidas",
                content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping("/aplicar-descuento")
    public ResponseEntity<ApiResponseDTO<DescuentoResponseDTO>> aplicarDescuento(
            @RequestBody @Valid @NotEmpty(message = "La lista de ventas no puede estar vacía") List<@Valid VentaDTO> ventas,
            @Parameter(description = "Porcentaje de descuento a aplicar, entre 0 y 100")
            @RequestParam double porcentaje) {

        DescuentoResponseDTO resultado = ventaService.aplicarDescuento(ventas, porcentaje);

        ApiResponseDTO<DescuentoResponseDTO> respuesta = new ApiResponseDTO<>(
                200, "Operacion realizada con exito", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
