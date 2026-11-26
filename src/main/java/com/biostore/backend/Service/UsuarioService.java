package com.biostore.backend.Service;
import com.biostore.backend.Repository.CarritoRepository;
import com.biostore.backend.Repository.RolRepository;
import com.biostore.backend.Repository.UsuarioRepository;
import java.util.List;

import org.springframework.stereotype.Service;
import com.biostore.backend.DTO.UsuarioDTO;
import com.biostore.backend.Model.Carrito;
import com.biostore.backend.Model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final CarritoRepository carritoRepository;

    public UsuarioDTO toDTO(Usuario usuario){
        
        UsuarioDTO dto = new UsuarioDTO();

        dto.setId(usuario.getId());
        dto.setPassword(usuario.getPassword());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setNumeroTelefonico(usuario.getNumeroTelefonico());
        dto.setNombreRol(usuario.getRol().getNombreRol());
        dto.setRolId(usuario.getRol().getId());

        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto){
        Usuario entidad = new Usuario();

        entidad.setPassword(dto.getPassword());
        entidad.setNombre(dto.getNombre());
        entidad.setEmail(dto.getEmail());
        entidad.setNumeroTelefonico(dto.getNumeroTelefonico());
        entidad.setRol(rolRepository.getReferenceById(dto.getRolId()));

        return entidad;
    }

    public List<UsuarioDTO> listarUsuarios(){
        return usuarioRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    public UsuarioDTO obtenerUsuarioPorCorreo(String correo){
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("No existe un usuario con correo: " + correo));
        return toDTO(usuario);
    }

    public UsuarioDTO crearUsuario(UsuarioDTO dto){
        Usuario usuario = toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        Carrito carrito = new Carrito();
        carrito.setUsuario(guardado);
        carritoRepository.save(carrito);
        return toDTO(guardado);
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO dto){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar un usuario con el id: " + id));

        usuario.setPassword(dto.getPassword());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setNumeroTelefonico(dto.getNumeroTelefonico());
        usuario.setRol(rolRepository.getReferenceById(dto.getRolId()));

        Usuario actualizado = usuarioRepository.save(usuario);
        return toDTO(actualizado);
    }

    public void eliminarUsuario(Integer id){
        if(!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado con el id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
