package com.biostore.backend.Service;

import com.biostore.backend.DTO.CarritoDTO;
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
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final ItemCarritoRepository itemCarritoRepository;
    private final ProductoRepository productoRepository;

    public CarritoDTO obtenerCarritoPorUsuario(Integer usuarioId) {
        Carrito carrito = buscarCarrito(usuarioId);
        return toDTO(carrito);
    }

    @Transactional
    public CarritoDTO agregarItem(Integer usuarioId, ItemCarritoDTO itemCarritoDTO) {
        if (itemCarritoDTO.getCantidad() == null || itemCarritoDTO.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }

        Carrito carrito = buscarCarrito(usuarioId);
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
            itemCarritoRepository.save(itemExistente);
        } else {
            if (producto.getStockProducto() < itemCarritoDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
            }
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(itemCarritoDTO.getCantidad());
            carrito.getItems().add(nuevoItem);
            itemCarritoRepository.save(nuevoItem);
        }

        return toDTO(carrito);
    }

    @Transactional
    public CarritoDTO vaciarCarrito(Integer usuarioId) {
        Carrito carrito = buscarCarrito(usuarioId);
        itemCarritoRepository.deleteAll(carrito.getItems());
        carrito.getItems().clear();
        carritoRepository.save(carrito);
        return toDTO(carrito);
    }

    @Transactional
    public CarritoDTO eliminarItem(Integer usuarioId, Integer itemId) {
        Carrito carrito = buscarCarrito(usuarioId);
        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + itemId));

        if (!item.getCarrito().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuario");
        }

        carrito.getItems().remove(item);
        itemCarritoRepository.delete(item);
        return toDTO(carrito);
    }

    private Carrito buscarCarrito(Integer usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuario con id: " + usuarioId));
    }

    public CarritoDTO toDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        if (carrito.getUsuario() != null) {
            dto.setUsuarioId(carrito.getUsuario().getId());
        }

        List<Integer> itemsIds = carrito.getItems().stream()
                .map(ItemCarrito::getId)
                .toList();
        dto.setItemsIds(itemsIds);

        return dto;
    }
}