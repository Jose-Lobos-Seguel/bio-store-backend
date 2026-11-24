package com.biostore.backend.Service;

import com.biostore.backend.DTO.RegionDTO;
import com.biostore.backend.Model.Region;
import com.biostore.backend.Repository.RegionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionDTO> listarRegiones() {
        return regionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public RegionDTO obtenerRegionPorId(Integer id) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + id));
        return toDTO(region);
    }

    @Transactional
    public RegionDTO crearRegion(RegionDTO dto) {
        Region region = toEntity(dto);
        Region guardada = regionRepository.save(region);
        return toDTO(guardada);
    }

    @Transactional
    public RegionDTO actualizarRegion(Integer id, RegionDTO dto) {
        Region region = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Region no encontrada con id: " + id));

        region.setNombreRegion(dto.getNombreRegion());

        Region actualizada = regionRepository.save(region);
        return toDTO(actualizada);
    }

    @Transactional
    public void eliminarRegion(Integer id) {
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Region no encontrada con id: " + id);
        }
        regionRepository.deleteById(id);
    }

    public RegionDTO toDTO(Region region) {
        RegionDTO dto = new RegionDTO();
        dto.setId(region.getId());
        dto.setNombreRegion(region.getNombreRegion());
        return dto;
    }

    public Region toEntity(RegionDTO dto) {
        Region entidad = new Region();
        entidad.setNombreRegion(dto.getNombreRegion());
        return entidad;
    }
}