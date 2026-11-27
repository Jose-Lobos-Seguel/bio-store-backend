package com.biostore.backend.Service;

import com.biostore.backend.DTO.ProductoDTO;
import com.biostore.backend.Model.Imagen;
import com.biostore.backend.Model.Producto;
import com.biostore.backend.Repository.FranquiciaRepository;
import com.biostore.backend.Repository.ProductoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final FranquiciaRepository franquiciaRepository;

    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ProductoDTO> listarPorFranquicia(Integer franquiciaId) {
        return productoRepository.findByFranquiciaId(franquiciaId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ProductoDTO obtenerPorId(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return toDTO(producto);
    }

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto) {
        if (dto.getFranquiciaId() == null || !franquiciaRepository.existsById(dto.getFranquiciaId())) {
            throw new RuntimeException("Franquicia no encontrada con id: " + dto.getFranquiciaId());
        }

        Producto producto = new Producto();
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setDescripcionProducto(dto.getDescripcionProducto());
        producto.setPrecioProducto(dto.getPrecioProducto());
        producto.setStockProducto(dto.getStockProducto());
        producto.setProductoActivo(dto.getProductoActivo() != null ? dto.getProductoActivo() : true);
        producto.setFranquicia(franquiciaRepository.getReferenceById(dto.getFranquiciaId()));

        return toDTO(productoRepository.save(producto));
    }

    @Transactional
    public ProductoDTO actualizarProducto(Integer id, ProductoDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        if (dto.getFranquiciaId() != null) {
            if (!franquiciaRepository.existsById(dto.getFranquiciaId())) {
                throw new RuntimeException("Franquicia no encontrada con id: " + dto.getFranquiciaId());
            }
            producto.setFranquicia(franquiciaRepository.getReferenceById(dto.getFranquiciaId()));
        }

        if (dto.getNombreProducto() != null) {
            producto.setNombreProducto(dto.getNombreProducto());
        }
        if (dto.getDescripcionProducto() != null) {
            producto.setDescripcionProducto(dto.getDescripcionProducto());
        }
        if (dto.getPrecioProducto() != null) {
            producto.setPrecioProducto(dto.getPrecioProducto());
        }
        if (dto.getStockProducto() != null) {
            producto.setStockProducto(dto.getStockProducto());
        }
        if (dto.getProductoActivo() != null) {
            producto.setProductoActivo(dto.getProductoActivo());
        }

        return toDTO(productoRepository.save(producto));
    }

    @Transactional
    public void eliminarProducto(Integer id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id: " + id);
        }
        productoRepository.deleteById(id);
    }

    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombreProducto(producto.getNombreProducto());
        dto.setDescripcionProducto(producto.getDescripcionProducto());
        dto.setPrecioProducto(producto.getPrecioProducto());
        dto.setStockProducto(producto.getStockProducto());
        dto.setProductoActivo(producto.getProductoActivo());
        dto.setNombreFranquicia(producto.getFranquicia().getNombreFranquicia());

        if (producto.getFranquicia() != null) {
            dto.setFranquiciaId(producto.getFranquicia().getId());
        }
        if (producto.getImagenes() != null) {
            dto.setImagenesIds(producto.getImagenes().stream()
                    .map(Imagen::getId)
                    .toList());
        }

        return dto;
    }
}