package AyDS2.TP2.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import AyDS2.TP2.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errores = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.add(error.getField() + ": " + error.getDefaultMessage());
        });

        ApiResponse<Object> respuesta = new ApiResponse<>(
                400,
                "Error de validación en los datos enviados",
                errores
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericError(Exception ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(
                500,
                "Ocurrió un error interno: " + ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(400, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Object>> handleProductoNoEncontrado(ProductoNoEncontradoException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(404, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleExternalApiError(ExternalApiException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(502, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(respuesta);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(400, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    @ExceptionHandler(EmailYaRegistradoException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailDuplicado(EmailYaRegistradoException ex) {

        ApiResponse<Object> respuesta = new ApiResponse<>(400, ex.getMessage(), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}
