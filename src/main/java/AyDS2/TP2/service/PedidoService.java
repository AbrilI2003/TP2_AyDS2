package AyDS2.TP2.service;

import AyDS2.TP2.dto.PedidoResponseDTO;
import AyDS2.TP2.dto.ProductoPedidoDTO;
import AyDS2.TP2.entity.DetallePedido;
import AyDS2.TP2.entity.Pedido;
import AyDS2.TP2.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscar(Integer clienteId, String categoria,
                                           LocalDate fechaDesde, LocalDate fechaHasta,
                                           String estado) {

        List<Pedido> pedidos = pedidoRepository.buscarConFiltros(
                clienteId, categoria, fechaDesde, fechaHasta, estado);

        return pedidos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private PedidoResponseDTO convertirADTO(Pedido pedido) {

        List<ProductoPedidoDTO> productos = pedido.getDetalles().stream()
                .map(this::convertirDetalleADTO)
                .collect(Collectors.toList());

        BigDecimal totalPedido = productos.stream()
                .map(ProductoPedidoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String nombreCliente = pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido();

        return new PedidoResponseDTO(
                pedido.getId(),
                nombreCliente,
                pedido.getFechaPedido(),
                pedido.getEstado(),
                totalPedido,
                productos
        );
    }

    private ProductoPedidoDTO convertirDetalleADTO(DetallePedido detalle) {

        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(detalle.getCantidad()));

        return new ProductoPedidoDTO(
                detalle.getProducto().getNombre(),
                detalle.getProducto().getCategoria().getNombre(),
                detalle.getCantidad(),
                subtotal
        );
    }
}