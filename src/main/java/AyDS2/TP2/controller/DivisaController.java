package AyDS2.TP2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AyDS2.TP2.dto.ApiResponseDTO;
import AyDS2.TP2.dto.ConversionDTO;
import AyDS2.TP2.dto.HistorialItemDTO;
import AyDS2.TP2.service.DivisaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/divisas")
@Tag(name = "Calculadora de Divisas")
public class DivisaController {

    private final DivisaService divisaService;

    public DivisaController(DivisaService divisaService) {
        this.divisaService = divisaService;
    }

    /*---------------------------------------------------------------------------------------------------- */

    @Operation(summary = "Convierte un monto entre dos monedas usando cotizaciones actuales (Ejercicio 3)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conversión realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "502", description = "Error con el servicio externo")
    })
    @GetMapping("/convertir")
    public ResponseEntity<ApiResponseDTO<ConversionDTO>> convertir(
            @RequestParam double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        ConversionDTO conversion = divisaService.convertir(monto, origen, destino);

        ApiResponseDTO<ConversionDTO> respuesta = new ApiResponseDTO<>(
                200, "Operacion realizada con exito", conversion);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    /*---------------------------------------------------------------------------------------------------- */

    @Operation(summary = "Consultar cotización y guardar en historial (Ejercicio 6-Endpoint 1)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "502", description = "Error con el servicio externo")
    })
    @PostMapping("/consultar")
    public ResponseEntity<ApiResponseDTO<ConversionDTO>> consultar(
            @Parameter(description = "Monto a convertir")
            @RequestParam double monto,
            @Parameter(description = "Moneda origen (3 letras)")
            @RequestParam String origen,
            @Parameter(description = "Moneda destino (3 letras)")
            @RequestParam String destino) {

        ConversionDTO resultado = divisaService.consultarYGuardar(monto, origen, destino);

        ApiResponseDTO<ConversionDTO> respuesta = new ApiResponseDTO<>(
                200, "Consulta realizada correctamente", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    /*---------------------------------------------------------------------------------------------------- */

    @Operation(summary = "Historial de cotizaciones por par de monedas (Ejercicio 6-Endpoint 2)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @GetMapping("/historial")
    public ResponseEntity<ApiResponseDTO<List<HistorialItemDTO>>> historial(
            @Parameter(description = "Moneda origen (3 letras)")
            @RequestParam String origen,
            @Parameter(description = "Moneda destino (3 letras)")
            @RequestParam String destino) {

        List<HistorialItemDTO> resultado = divisaService.obtenerHistorial(origen, destino);

        ApiResponseDTO<List<HistorialItemDTO>> respuesta = new ApiResponseDTO<>(
                200, "Historial obtenido correctamente", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}