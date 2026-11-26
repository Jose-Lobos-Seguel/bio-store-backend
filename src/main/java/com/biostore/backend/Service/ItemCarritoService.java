package com.biostore.backend.Service;

import com.biostore.backend.DTO.ItemCarritoDTO;
import com.biostore.backend.Model.Carrito;
import com.biostore.backend.Model.ItemCarrito;
import com.biostore.backend.Model.Producto;
import com.biostore.backend.Repository.CarritoRepository;
import com.biostore.backend.Repository.ItemCarritoRepository;
import com.biostore.backend.Repository.ProductoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemCarritoService {

    private final ItemCarritoRepository itemCarritoRepository;
    private final CarritoRepository carritoRepository;
    private final ProductoRepository productoRepository;

    public List<ItemCarritoDTO> listarPorCarrito(Integer carritoId) {
        return itemCarritoRepository.findByCarritoId(carritoId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ItemCarritoDTO obtenerPorId(Integer id) {
        ItemCarrito item = itemCarritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado con id: " + id));
        return toDTO(item);
    }

    @Transactional
    public ItemCarritoDTO guardarItem(ItemCarritoDTO itemCarritoDTO) {
        if (itemCarritoDTO.getCantidad() == null || itemCarritoDTO.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Carrito carrito = carritoRepository.findById(itemCarritoDTO.getCarritoId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + itemCarritoDTO.getCarritoId()));

        Producto producto = productoRepository.findById(itemCarritoDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + itemCarritoDTO.getProductoId()));

        ItemCarrito itemExistente = itemCarritoRepository
                .findByCarritoIdAndProductoId(carrito.getId(), producto.getId())
                .orElse(null);

        if (itemExistente != null) {
            int nuevaCantidad = itemExistente.getCantidad() + itemCarritoDTO.getCantidad();
            if (producto.getStockProducto() < nuevaCantidad) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
            }
            itemExistente.setCantidad(nuevaCantidad);
            return toDTO(itemCarritoRepository.save(itemExistente));
        }

        if (producto.getStockProducto() < itemCarritoDTO.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
        }

        ItemCarrito nuevoItem = new ItemCarrito();
        nuevoItem.setCarrito(carrito);
        nuevoItem.setProducto(producto);
        nuevoItem.setCantidad(itemCarritoDTO.getCantidad());

        return toDTO(itemCarritoRepository.save(nuevoItem));
    }

    @Transactional
    public ItemCarritoDTO actualizarCantidad(Integer id, Integer cantidad) {
        ItemCarrito item = itemCarritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado con id: " + id));

        if (cantidad == null || cantidad <= 0) {
            itemCarritoRepository.delete(item);
            return null;
        }

        if (item.getProducto().getStockProducto() < cantidad) {
            throw new RuntimeException("Stock insuficiente para: " + item.getProducto().getNombreProducto());
        }

        item.setCantidad(cantidad);
        return toDTO(itemCarritoRepository.save(item));
    }

    @Transactional
    public void eliminarItem(Integer id) {
        if (!itemCarritoRepository.existsById(id)) {
            throw new RuntimeException("Item de carrito no encontrado con id: " + id);
        }
        itemCarritoRepository.deleteById(id);
    }

    public ItemCarritoDTO toDTO(ItemCarrito item) {
        ItemCarritoDTO dto = new ItemCarritoDTO();
        dto.setId(item.getId());
        if (item.getCarrito() != null) {
            dto.setCarritoId(item.getCarrito().getId());
        }
        if (item.getProducto() != null) {
            dto.setProductoId(item.getProducto().getId());
        }
        dto.setCantidad(item.getCantidad());
        return dto;
    }
}