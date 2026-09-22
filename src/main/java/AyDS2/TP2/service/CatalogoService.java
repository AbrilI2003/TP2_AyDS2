package AyDS2.TP2.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import AyDS2.TP2.dto.ProductoDTO;
import AyDS2.TP2.exception.ProductoNoEncontradoException;
import AyDS2.TP2.model.Producto;

@Service
public class CatalogoService {

    private final List<Producto> productos = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public CatalogoService() {
        productos.add(new Producto(idGenerator.getAndIncrement(), "Mouse inalambrico", "Perifericos", 4500.0, 20));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Teclado mecanico", "Perifericos", 25000.0, 10));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Monitor 24 pulgadas", "Monitores", 85000.0, 5));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Notebook Lenovo", "Notebooks", 450000.0, 3));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Auriculares Bluetooth", "Audio", 18000.0, 15));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Webcam HD", "Perifericos", 12000.0, 8));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Silla gamer", "Muebles", 120000.0, 4));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Mousepad XL", "Perifericos", 3500.0, 30));
        productos.add(new Producto(idGenerator.getAndIncrement(), "Soporte Notebook", "Perifericos", 7500.0, 30));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public List<Producto> buscar(String categoria, Double precioMin, Double precioMax) {

        return productos.stream()
                .filter(p -> categoria == null || p.getCategoria().equalsIgnoreCase(categoria))
                .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
                .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> ordenar(String criterio, String orden) {

        Comparator<Producto> comparator;

        if ("precio".equalsIgnoreCase(criterio)) {
            comparator = Comparator.comparingDouble(Producto::getPrecio);
        } else {
            comparator = Comparator.comparing(Producto::getNombre);
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparator = comparator.reversed();
        }

        return productos.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public Producto agregar(ProductoDTO productoDTO) {

        Producto nuevoProducto = new Producto(
                idGenerator.getAndIncrement(),
                productoDTO.getNombre(),
                productoDTO.getCategoria(),
                productoDTO.getPrecio(),
                productoDTO.getStock()
        );

        productos.add(nuevoProducto);

        return nuevoProducto;
    }

    public Producto modificarStock(Long id, int cantidad) {

        Producto producto = productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException("No existe un producto con id " + id));

        int nuevoStock = producto.getStock() + cantidad;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException("El stock no puede quedar por debajo de 0");
        }

        producto.setStock(nuevoStock);

        return producto;
    }

    public void eliminar(Long id) {

        Producto producto = productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductoNoEncontradoException("No existe un producto con id " + id));

        productos.remove(producto);
    }
}
