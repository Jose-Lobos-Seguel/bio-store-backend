package com.biostore.backend.Service;

import com.biostore.backend.DTO.VentaDTO;
import com.biostore.backend.Model.Carrito;
import com.biostore.backend.Model.DetalleVenta;
import com.biostore.backend.Model.ItemCarrito;
import com.biostore.backend.Model.Producto;
import com.biostore.backend.Model.Venta;
import com.biostore.backend.Repository.CarritoRepository;
import com.biostore.backend.Repository.DireccionRepository;
import com.biostore.backend.Repository.ProductoRepository;
import com.biostore.backend.Repository.VentaRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;
    private final DireccionRepository direccionRepository;

    public List<VentaDTO> listarVentas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<VentaDTO> listarPorUsuario(Integer usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public VentaDTO obtenerVentaPorId(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
        return toDTO(venta);
    }

    @Transactional
    public VentaDTO realizarCompraDesdeCarrito(Integer usuarioId, Integer direccionId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario con id: " + usuarioId));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        if (direccionId != null && !direccionRepository.existsById(direccionId)) {
            throw new RuntimeException("Dirección no encontrada con id: " + direccionId);
        }

        Venta venta = new Venta();
        venta.setUsuario(carrito.getUsuario());
        if (direccionId != null) {
            venta.setDireccion(direccionRepository.getReferenceById(direccionId));
        }

        BigDecimal totalVenta = BigDecimal.ZERO;

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + item.getProducto().getId()));

            if (producto.getStockProducto() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
            }

            producto.setStockProducto(producto.getStockProducto() - item.getCantidad());
            productoRepository.save(producto);

            BigDecimal precioUnitario = producto.getPrecioProducto();
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(item.getCantidad()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setSubtotal(subtotal);

            venta.getDetalles().add(detalle);
            totalVenta = totalVenta.add(subtotal);
        }

        venta.setTotal(totalVenta);
        Venta guardada = ventaRepository.save(venta);

        carrito.getItems().clear();
        carritoRepository.save(carrito);

        return toDTO(guardada);
    }

    public VentaDTO toDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        if (venta.getUsuario() != null) {
            dto.setUsuarioId(venta.getUsuario().getId());
        }
        if (venta.getDireccion() != null) {
            dto.setDireccionId(venta.getDireccion().getId());
        }
        dto.setTotal(venta.getTotal());

        List<Integer> detallesIds = venta.getDetalles().stream()
                .map(DetalleVenta::getId)
                .toList();
        dto.setDetalles(detallesIds);

        return dto;
    }
}