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

import AyDS2.TP2.dto.ApiResponse;
import AyDS2.TP2.dto.ClienteDTO;
import AyDS2.TP2.entity.Cliente;
import AyDS2.TP2.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Cliente creado correctamente, incluye el id generado",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> altaSimple(@RequestBody ClienteDTO clienteDTO) {

        Cliente cliente = clienteService.altaSimple(clienteDTO);
        ApiResponse<Cliente> respuesta = new ApiResponse<>(201, "Operacion realizada con exito", cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(
            summary = "Registra un nuevo cliente, con validaciones y chequeo de email duplicado",
            description = "Valida nombre, apellido, email y teléfono; rechaza emails ya registrados."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Cliente creado correctamente, incluye el id generado",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400", description = "Datos inválidos (errores agrupados por campo) o email ya registrado",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<Object>> altaValidada(
            @RequestBody @Valid ClienteDTO clienteDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));

            ApiResponse<Object> respuestaError = new ApiResponse<>(400, "Error de validacion", errores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        }

        Cliente cliente = clienteService.altaValidada(clienteDTO);
        ApiResponse<Object> respuesta = new ApiResponse<>(201, "Operacion realizada con exito", cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}