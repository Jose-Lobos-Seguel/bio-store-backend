package com.biostore.backend.Service;

import com.biostore.backend.DTO.ImagenDTO;
import com.biostore.backend.Model.Imagen;
import com.biostore.backend.Repository.ImagenRepository;
import com.biostore.backend.Repository.ProductoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImagenService {

    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;

    public List<ImagenDTO> listarPorProducto(Integer productoId) {
        return imagenRepository.findByProductoId(productoId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public ImagenDTO crearImagen(ImagenDTO dto) {
        if (dto.getProductoId() == null || !productoRepository.existsById(dto.getProductoId())) {
            throw new RuntimeException("Producto no encontrado con id: " + dto.getProductoId());
        }
        if (dto.getUrlImagen() == null || dto.getUrlImagen().isBlank()) {
            throw new RuntimeException("La URL de la imagen es obligatoria");
        }

        Imagen imagen = new Imagen();
        imagen.setProducto(productoRepository.getReferenceById(dto.getProductoId()));
        imagen.setUrlImagen(dto.getUrlImagen());

        return toDTO(imagenRepository.save(imagen));
    }

    @Transactional
    public void eliminarImagen(Integer id) {
        if (!imagenRepository.existsById(id)) {
            throw new RuntimeException("Imagen no encontrada con id: " + id);
        }
        imagenRepository.deleteById(id);
    }

    public ImagenDTO toDTO(Imagen imagen) {
        ImagenDTO dto = new ImagenDTO();
        dto.setId(imagen.getId());
        if (imagen.getProducto() != null) {
            dto.setProductoId(imagen.getProducto().getId());
        }
        dto.setUrlImagen(imagen.getUrlImagen());
        return dto;
    }
}