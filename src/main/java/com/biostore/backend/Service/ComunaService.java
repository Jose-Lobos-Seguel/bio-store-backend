package com.biostore.backend.Service;

import com.biostore.backend.DTO.ComunaDTO;
import com.biostore.backend.Model.Comuna;
import com.biostore.backend.Repository.ComunaRepository;
import com.biostore.backend.Repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComunaService {

    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;

    public List<ComunaDTO> listarComunas() {
        return comunaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ComunaDTO obtenerComunaPorId(Integer id) {
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + id));
        return toDTO(comuna);
    }

    @Transactional
    public ComunaDTO crearComuna(ComunaDTO dto) {
        Comuna comuna = toEntity(dto);
        Comuna guardada = comunaRepository.save(comuna);
        return toDTO(guardada);
    }

    @Transactional
    public ComunaDTO actualizarComuna(Integer id, ComunaDTO dto) {
        Comuna comuna = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + id));

        comuna.setNombreComuna(dto.getNombreComuna());
        comuna.setRegion(regionRepository.getReferenceById(dto.getRegionId()));

        Comuna actualizada = comunaRepository.save(comuna);
        return toDTO(actualizada);
    }

    @Transactional
    public void eliminarComuna(Integer id) {
        if (!comunaRepository.existsById(id)) {
            throw new RuntimeException("Comuna no encontrada con id: " + id);
        }
        comunaRepository.deleteById(id);
    }

    public ComunaDTO toDTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setId(comuna.getId());
        dto.setNombreComuna(comuna.getNombreComuna());

        if (comuna.getRegion() != null) {
            dto.setRegionId(comuna.getRegion().getId());
        }
        return dto;
    }

    public Comuna toEntity(ComunaDTO dto) {
        Comuna entidad = new Comuna();
        entidad.setNombreComuna(dto.getNombreComuna());
        entidad.setRegion(regionRepository.getReferenceById(dto.getRegionId()));
        return entidad;
    }
}