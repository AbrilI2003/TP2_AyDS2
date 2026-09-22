package AyDS2.TP2.controller;

import AyDS2.TP2.dto.ApiResponse;
import AyDS2.TP2.dto.ConversionDTO;
import AyDS2.TP2.dto.HistorialItemDTO;
import AyDS2.TP2.service.DivisaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/divisas")
@Validated
public class DivisaController {

    private final DivisaService divisaService;

    public DivisaController(DivisaService divisaService) {
        this.divisaService = divisaService;
    }

    @Operation(summary = "Convierte un monto entre dos monedas usando cotizaciones actuales")
    @GetMapping("/convertir")
    public ResponseEntity<ApiResponse<ConversionDTO>> convertir(
            @RequestParam @Positive(message = "El monto debe ser mayor que 0") double monto,
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de origen debe tener 3 letras") String origen,
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de destino debe tener 3 letras") String destino) {

        ConversionDTO conversion = divisaService.convertir(monto, origen, destino);

        ApiResponse<ConversionDTO> respuesta = new ApiResponse<>(
                200, "Operacion realizada con exito", conversion);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Consultar cotización y guardar en historial")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", description = "Consulta realizada correctamente",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400", description = "Datos inválidos",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "502", description = "Error con el servicio externo",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse<ConversionDTO>> consultar(
            @Parameter(description = "Monto a convertir")
            @RequestParam @Positive(message = "El monto debe ser mayor que 0") double monto,
            @Parameter(description = "Moneda origen (3 letras)")
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de origen debe tener 3 letras") String origen,
            @Parameter(description = "Moneda destino (3 letras)")
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de destino debe tener 3 letras") String destino) {

        ConversionDTO resultado = divisaService.consultarYGuardar(monto, origen, destino);

        ApiResponse<ConversionDTO> respuesta = new ApiResponse<>(
                200, "Consulta realizada correctamente", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Historial de cotizaciones por par de monedas")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200", description = "Historial obtenido correctamente",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400", description = "Datos inválidos",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<HistorialItemDTO>>> historial(
            @Parameter(description = "Moneda origen (3 letras)")
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de origen debe tener 3 letras") String origen,
            @Parameter(description = "Moneda destino (3 letras)")
            @RequestParam @Pattern(regexp = "^[A-Za-z]{3}$",
                    message = "El código de moneda de destino debe tener 3 letras") String destino) {

        List<HistorialItemDTO> resultado = divisaService.obtenerHistorial(origen, destino);

        ApiResponse<List<HistorialItemDTO>> respuesta = new ApiResponse<>(
                200, "Historial obtenido correctamente", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
