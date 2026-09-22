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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Registra un nuevo cliente (sin validaciones adicionales)")
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> altaSimple(@RequestBody ClienteDTO clienteDTO) {

        Cliente cliente = clienteService.altaSimple(clienteDTO);

        ApiResponse<Cliente> respuesta = new ApiResponse<>(201, "Operacion realizada con exito", cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Registra un nuevo cliente, con validaciones y chequeo de email duplicado")
    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<Object>> altaValidada(
            @RequestBody @Valid ClienteDTO clienteDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error
                    -> errores.put(error.getField(), error.getDefaultMessage())
            );

            ApiResponse<Object> respuestaError = new ApiResponse<>(400, "Error de validacion", errores);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        }

        Cliente cliente = clienteService.altaValidada(clienteDTO);

        ApiResponse<Object> respuesta = new ApiResponse<>(201, "Operacion realizada con exito", cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
