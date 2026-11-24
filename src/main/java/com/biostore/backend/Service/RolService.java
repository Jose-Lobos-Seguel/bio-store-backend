package com.biostore.backend.Service;

import com.biostore.backend.Repository.RolRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biostore.backend.DTO.RolDTO;
import com.biostore.backend.Model.Rol;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    
    private final RolRepository rolRepository;

    public RolDTO toDTO(Rol rol){

        RolDTO dto = new RolDTO();

        dto.setId(rol.getId());
        dto.setNombreRol(rol.getNombreRol());

        return dto;
    }

    public Rol toEntity(RolDTO dto){

        Rol entidad = new Rol();

        entidad.setNombreRol(dto.getNombreRol());

        return entidad;
    }

    public List<RolDTO> listarRoles(){
        return rolRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public RolDTO obtenerRolPorId(Integer id){
        Rol rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un rol con el id: " + id));
        return toDTO(rol);    
    }

    public RolDTO crearRol(RolDTO dto){
        Rol rol = toEntity(dto);
        Rol guardado = rolRepository.save(rol);
        return toDTO(guardado);
    }

    public RolDTO actualizarRol(Integer id, RolDTO dto){
        Rol rol = rolRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el rol con el id: " + id));
        rol.setNombreRol(dto.getNombreRol());

        Rol actualizado = rolRepository.save(rol);
        return toDTO(actualizado);
    }

    public void eliminarRol(Integer id){
        if(!rolRepository.existsById(id)){
            throw new RuntimeException("Rol no encontrado con el id: " + id);
        }
        rolRepository.deleteById(id);
    }
}
