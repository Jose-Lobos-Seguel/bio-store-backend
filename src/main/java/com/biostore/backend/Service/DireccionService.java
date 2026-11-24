package com.biostore.backend.Service;

import com.biostore.backend.DTO.DireccionDTO;
import com.biostore.backend.Model.Direccion;
import com.biostore.backend.Repository.ComunaRepository;
import com.biostore.backend.Repository.DireccionRepository;
import com.biostore.backend.Repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComunaRepository comunaRepository;

    public DireccionDTO toDTO(Direccion direccion) {
        DireccionDTO dto = new DireccionDTO();
        dto.setId(direccion.getId());
        dto.setCalle(direccion.getNombreCalle());
        dto.setNumero(direccion.getNumeroVivienda());
        dto.setReferencia(direccion.getReferencia());

        if (direccion.getUsuario() != null) {
            dto.setUsuarioId(direccion.getUsuario().getId());
        }
        if (direccion.getComuna() != null) {
            dto.setComunaId(direccion.getComuna().getId());
        }
        return dto;
    }

    public Direccion toEntity(DireccionDTO dto) {
        Direccion entidad = new Direccion();
        entidad.setNombreCalle(dto.getCalle());
        entidad.setNumeroVivienda(dto.getNumero());
        entidad.setReferencia(dto.getReferencia());
        entidad.setUsuario(usuarioRepository.getReferenceById(dto.getUsuarioId()));
        entidad.setComuna(comunaRepository.getReferenceById(dto.getComunaId()));
        return entidad;
    }

    public List<DireccionDTO> listarDirecciones() {
        return direccionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<DireccionDTO> listarPorUsuario(Integer usuarioId) {
        return direccionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public DireccionDTO obtenerDireccionPorId(Integer id) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Direccion no encontrada con id: " + id));
        return toDTO(direccion);
    }

    @Transactional
    public DireccionDTO crearDireccion(DireccionDTO dto) {
        Direccion direccion = toEntity(dto);
        Direccion guardada = direccionRepository.save(direccion);
        return toDTO(guardada);
    }

    @Transactional
    public DireccionDTO actualizarDireccion(Integer id, DireccionDTO dto) {
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Direccion no encontrada con id: " + id));

        direccion.setNombreCalle(dto.getCalle());
        direccion.setNumeroVivienda(dto.getNumero());
        direccion.setReferencia(dto.getComplemento());
        direccion.setUsuario(usuarioRepository.getReferenceById(dto.getUsuarioId()));
        direccion.setComuna(comunaRepository.getReferenceById(dto.getComunaId()));

        Direccion actualizada = direccionRepository.save(direccion);
        return toDTO(actualizada);
    }

    @Transactional
    public void eliminarDireccion(Integer id) {
        if (!direccionRepository.existsById(id)) {
            throw new RuntimeException("Direccion no encontrada con id: " + id);
        }
        direccionRepository.deleteById(id);
    }
}