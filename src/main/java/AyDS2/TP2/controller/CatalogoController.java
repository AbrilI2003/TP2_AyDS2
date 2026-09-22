package AyDS2.TP2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import AyDS2.TP2.dto.ApiResponse;
import AyDS2.TP2.dto.ProductoDTO;
import AyDS2.TP2.model.Producto;
import AyDS2.TP2.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(summary = "Devuelve todos los productos del catálogo")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {

        List<Producto> productos = catalogoService.obtenerTodos();
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", productos);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(
            summary = "Busca productos por categoría y/o rango de precio",
            description = "Todos los parámetros son opcionales y se combinan con AND."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "Búsqueda realizada correctamente (puede devolver lista vacía)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscar(
            @Parameter(description = "Filtra por categoría exacta")
            @RequestParam(required = false) String categoria,
            @Parameter(description = "Precio mínimo (inclusive)")
            @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio máximo (inclusive)")
            @RequestParam(required = false) Double precioMax) {

        List<Producto> resultado = catalogoService.buscar(categoria, precioMin, precioMax);
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", resultado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Ordena los productos por precio o nombre")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "Listado ordenado correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/ordenar")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenar(
            @Parameter(description = "Criterio de orden: precio o nombre")
            @RequestParam String criterio,
            @Parameter(description = "Dirección: asc (por defecto) o desc")
            @RequestParam(required = false, defaultValue = "asc") String orden) {

        List<Producto> resultado = catalogoService.ordenar(criterio, orden);
        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", resultado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Agrega un nuevo producto al catálogo")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201", description = "Producto creado correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400", description = "Datos inválidos (nombre vacío, precio o stock incorrectos)",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> agregar(@RequestBody @Valid ProductoDTO productoDTO) {

        Producto nuevoProducto = catalogoService.agregar(productoDTO);
        ApiResponse<Producto> respuesta = new ApiResponse<>(201, "Operacion realizada con exito", nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(
            summary = "Modifica el stock de un producto",
            description = "'cantidad' positiva aumenta el stock, negativa lo disminuye."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "Stock actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400", description = "El stock quedaría negativo",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "No existe un producto con ese id",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Producto>> modificarStock(
            @Parameter(description = "Id del producto") @PathVariable Long id,
            @Parameter(description = "Cuánto sumar (positivo) o restar (negativo) al stock actual")
            @RequestParam int cantidad) {

        Producto producto = catalogoService.modificarStock(id, cantidad);
        ApiResponse<Producto> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", producto);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Elimina un producto del catálogo")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200", description = "Producto eliminado correctamente",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404", description = "No existe un producto con ese id",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(
            @Parameter(description = "Id del producto") @PathVariable Long id) {

        catalogoService.eliminar(id);
        ApiResponse<Object> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", null);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}