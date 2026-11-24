package com.biostore.backend.Controller;

import com.biostore.backend.DTO.DireccionDTO;
import com.biostore.backend.Service.DireccionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/direcciones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService direccionService;

    @GetMapping
    public ResponseEntity<List<DireccionDTO>> listarDirecciones() {
        return ResponseEntity.ok(direccionService.listarDirecciones());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(direccionService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionDTO> obtenerDireccionPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(direccionService.obtenerDireccionPorId(id));
    }

    @PostMapping
    public ResponseEntity<DireccionDTO> crearDireccion(@Valid @RequestBody DireccionDTO dto) {
        DireccionDTO creada = direccionService.crearDireccion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionDTO> actualizarDireccion(
            @PathVariable Integer id,
            @Valid @RequestBody DireccionDTO dto) {
        DireccionDTO actualizada = direccionService.actualizarDireccion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable Integer id) {
        direccionService.eliminarDireccion(id);
        return ResponseEntity.noContent().build();
    }
}