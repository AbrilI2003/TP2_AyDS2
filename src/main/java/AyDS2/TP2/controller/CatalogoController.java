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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(summary = "Devuelve todos los productos del catálogo")
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {

        List<Producto> productos = catalogoService.obtenerTodos();

        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(
                200,
                "Operacion realizada con exito",
                productos
        );

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Busca productos por categoría y/o rango de precio")
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscar(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {

        List<Producto> resultado = catalogoService.buscar(categoria, precioMin, precioMax);

        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Ordena los productos por precio o nombre")
    @GetMapping("/ordenar")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenar(
            @RequestParam String criterio,
            @RequestParam(required = false, defaultValue = "asc") String orden) {

        List<Producto> resultado = catalogoService.ordenar(criterio, orden);

        ApiResponse<List<Producto>> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", resultado);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Agrega un nuevo producto al catálogo")
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> agregar(@RequestBody @Valid ProductoDTO productoDTO) {

        Producto nuevoProducto = catalogoService.agregar(productoDTO);

        ApiResponse<Producto> respuesta = new ApiResponse<>(
                201,
                "Operacion realizada con exito",
                nuevoProducto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Modifica el stock de un producto")
    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<Producto>> modificarStock(
            @PathVariable Long id,
            @RequestParam int cantidad) {

        Producto producto = catalogoService.modificarStock(id, cantidad);

        ApiResponse<Producto> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", producto);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @Operation(summary = "Elimina un producto del catálogo")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> eliminar(@PathVariable Long id) {

        catalogoService.eliminar(id);

        ApiResponse<Object> respuesta = new ApiResponse<>(200, "Operacion realizada con exito", null);

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
