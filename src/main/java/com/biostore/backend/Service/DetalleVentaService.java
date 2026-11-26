package com.biostore.backend.Service;

import com.biostore.backend.DTO.DetalleVentaDTO;
import com.biostore.backend.Model.DetalleVenta;
import com.biostore.backend.Model.Producto;
import com.biostore.backend.Model.Venta;
import com.biostore.backend.Repository.DetalleVentaRepository;
import com.biostore.backend.Repository.ProductoRepository;
import com.biostore.backend.Repository.VentaRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public List<DetalleVentaDTO> listarPorVenta(Integer ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DetalleVentaDTO obtenerPorId(Integer id) {
        DetalleVenta detalle = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con id: " + id));
        return toDTO(detalle);
    }

    @Transactional
    public DetalleVentaDTO crearDetalle(DetalleVentaDTO dto) {
        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Venta venta = ventaRepository.findById(dto.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + dto.getVentaId()));

        Producto producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + dto.getProductoId()));

        if (producto.getStockProducto() < dto.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
        }

        producto.setStockProducto(producto.getStockProducto() - dto.getCantidad());
        productoRepository.save(producto);

        BigDecimal precioUnitario = dto.getPrecioUnitario() != null
                ? dto.getPrecioUnitario()
                : producto.getPrecioProducto();
        BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(dto.getCantidad()));

        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setSubtotal(subtotal);

        return toDTO(detalleVentaRepository.save(detalle));
    }

    @Transactional
    public void eliminarDetalle(Integer id) {
        if (!detalleVentaRepository.existsById(id)) {
            throw new RuntimeException("Detalle de venta no encontrado con id: " + id);
        }
        detalleVentaRepository.deleteById(id);
    }

    public DetalleVentaDTO toDTO(DetalleVenta detalle) {
        DetalleVentaDTO dto = new DetalleVentaDTO();
        if (detalle.getVenta() != null) {
            dto.setVentaId(detalle.getVenta().getId());
        }
        if (detalle.getProducto() != null) {
            dto.setProductoId(detalle.getProducto().getId());
        }
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubtotal(detalle.getSubtotal());
        return dto;
    }
}