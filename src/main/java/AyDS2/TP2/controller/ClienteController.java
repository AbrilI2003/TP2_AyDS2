package AyDS2.TP2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AyDS2.TP2.dto.ApiResponseDTO;
import AyDS2.TP2.dto.ClienteDTO;
import AyDS2.TP2.entity.Cliente;
import AyDS2.TP2.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Modulo de Clientes")
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(
            summary = "Registra un nuevo cliente (sin validaciones adicionales)",
            description = "Alta simple: inserta el cliente tal como llega, sin chequear formato ni duplicados."
    )
    @ApiResponse(responseCode = "201", description = "Cliente creado correctamente")
    @PostMapping
    public ResponseEntity<ApiResponseDTO<Cliente>> altaSimple(@RequestBody ClienteDTO clienteDTO) {

        Cliente cliente = clienteService.altaSimple(clienteDTO);
        ApiResponseDTO<Cliente> respuesta = new ApiResponseDTO<>(201, "Operacion realizada con exito", cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(
            summary = "Registra un nuevo cliente, con validaciones y chequeo de email duplicado",
            description = "Valida nombre, apellido, email y teléfono; rechaza emails ya registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente, incluye el id generado"), 
            @ApiResponse(responseCode = "400", description = "Datos inválidos (errores agrupados por campo) o email ya registrado",
            content = @Content(schema = @Schema(implementation = ApiResponseDTO.class)))
    })
    @PostMapping("/validado")
    public ResponseEntity<ApiResponseDTO<Object>> altaValidada(
            @RequestBody @Valid ClienteDTO clienteDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));

            ApiResponseDTO<Object> respuestaError = new ApiResponseDTO<>(400, "Error de validacion", errores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        }

        Cliente cliente = clienteService.altaValidada(clienteDTO);
        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(201, "Operacion realizada con exito", cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}