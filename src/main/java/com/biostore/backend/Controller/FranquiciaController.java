package com.biostore.backend.Controller;

import com.biostore.backend.DTO.FranquiciaDTO;
import com.biostore.backend.Service.FranquiciaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
public class FranquiciaController {

    private final FranquiciaService franquiciaService;

    @GetMapping
    public ResponseEntity<List<FranquiciaDTO>> listarFranquicias() {
        return ResponseEntity.ok(franquiciaService.listarFranquicias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FranquiciaDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(franquiciaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<FranquiciaDTO> crearFranquicia(@RequestBody FranquiciaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(franquiciaService.crearFranquicia(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FranquiciaDTO> actualizarFranquicia(
            @PathVariable Integer id,
            @RequestBody FranquiciaDTO dto) {
        return ResponseEntity.ok(franquiciaService.actualizarFranquicia(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFranquicia(@PathVariable Integer id) {
        franquiciaService.eliminarFranquicia(id);
        return ResponseEntity.noContent().build();
    }
}