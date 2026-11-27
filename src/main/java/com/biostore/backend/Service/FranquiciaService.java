package com.biostore.backend.Service;

import com.biostore.backend.DTO.FranquiciaDTO;
import com.biostore.backend.Model.Franquicia;
import com.biostore.backend.Repository.FranquiciaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;

    public List<FranquiciaDTO> listarFranquicias() {
        return franquiciaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public FranquiciaDTO obtenerPorId(Integer id) {
        Franquicia franquicia = franquiciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada con id: " + id));
        return toDTO(franquicia);
    }

    @Transactional
    public FranquiciaDTO crearFranquicia(FranquiciaDTO dto) {
        Franquicia franquicia = new Franquicia();
        franquicia.setNombreFranquicia(dto.getNombreFranquicia());
        franquicia.setFranquiciaActiva(dto.getFranquiciaActiva() != null ? dto.getFranquiciaActiva() : true);
        return toDTO(franquiciaRepository.save(franquicia));
    }

    @Transactional
    public FranquiciaDTO actualizarFranquicia(Integer id, FranquiciaDTO dto) {
        Franquicia franquicia = franquiciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franquicia no encontrada con id: " + id));

        franquicia.setNombreFranquicia(dto.getNombreFranquicia());
        if (dto.getFranquiciaActiva() != null) {
            franquicia.setFranquiciaActiva(dto.getFranquiciaActiva());
        }

        return toDTO(franquiciaRepository.save(franquicia));
    }

    @Transactional
    public void eliminarFranquicia(Integer id) {
        if (!franquiciaRepository.existsById(id)) {
            throw new RuntimeException("Franquicia no encontrada con id: " + id);
        }
        franquiciaRepository.deleteById(id);
    }

    public FranquiciaDTO toDTO(Franquicia franquicia) {
        FranquiciaDTO dto = new FranquiciaDTO();
        dto.setId(franquicia.getId());
        dto.setNombreFranquicia(franquicia.getNombreFranquicia());
        dto.setFranquiciaActiva(franquicia.getFranquiciaActiva());
        return dto;
    }
}