package AyDS2.TP2.controller;

import AyDS2.TP2.dto.ApiResponse;
import AyDS2.TP2.dto.PedidoResponseDTO;
import AyDS2.TP2.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Consulta de pedidos con filtros combinables")
public class PedidoController {

    private static final Set<String> ESTADOS_VALIDOS
            = Set.of("PENDIENTE", "ENVIADO", "ENTREGADO", "CANCELADO");

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
            summary = "Buscar pedidos",
            description = "Devuelve pedidos aplicando filtros opcionales. Si no se envía ningún filtro, devuelve todos."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Consulta realizada correctamente",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Parámetros inválidos",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "500",
                description = "Error interno",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ApiResponse.class))
        )
    })
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscar(
            @Parameter
            @RequestParam(required = false) Integer clienteId,
            @Parameter
            @RequestParam(required = false) String categoria,
            @Parameter(description = "Fecha desde (yyyy-mm-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @Parameter(description = "Fecha hasta (yyyy-mm-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,
            @Parameter(description = "Estado: PENDIENTE, ENVIADO, ENTREGADO o CANCELADO")
            @RequestParam(required = false) String estado) {

        if (clienteId != null && clienteId < 1) {
            throw new IllegalArgumentException("El clienteId debe ser mayor a 0");
        }

        if (estado != null && !ESTADOS_VALIDOS.contains(estado.toUpperCase())) {
            throw new IllegalArgumentException(
                    "El estado debe ser PENDIENTE, ENVIADO, ENTREGADO o CANCELADO");
        }

        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            throw new IllegalArgumentException(
                    "La fechaDesde no puede ser posterior a la fechaHasta");
        }

        List<PedidoResponseDTO> resultado = pedidoService.buscar(
                clienteId, categoria, fechaDesde, fechaHasta, estado);

        ApiResponse<List<PedidoResponseDTO>> respuesta = new ApiResponse<>(
                200,
                "Consulta realizada correctamente",
                resultado
        );

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
