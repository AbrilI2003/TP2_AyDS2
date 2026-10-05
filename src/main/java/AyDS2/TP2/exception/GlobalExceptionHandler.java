package AyDS2.TP2.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import AyDS2.TP2.dto.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errores = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.add(error.getField() + ": " + error.getDefaultMessage());
        });

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(
                400,
                "Error de validación en los datos enviados",
                errores
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGenericError(Exception ex) {

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(
                500,
                "Ocurrió un error interno: " + ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleIllegalArgument(IllegalArgumentException ex) {

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(400, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleProductoNoEncontrado(ProductoNoEncontradoException ex) {

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(404, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleExternalApiError(ExternalApiException ex) {

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(502, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(respuesta);
    }

    
@ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleEmailDuplicado(EmailYaRegistradoException ex) {

        ApiResponseDTO<Object> respuesta = new ApiResponseDTO<>(400, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

}