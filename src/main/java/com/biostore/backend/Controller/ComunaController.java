package com.biostore.backend.Controller;

import com.biostore.backend.DTO.ComunaDTO;
import com.biostore.backend.Service.ComunaService;
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
@RequestMapping("/api/comunas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ComunaController {

    private final ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarComunas() {
        return ResponseEntity.ok(comunaService.listarComunas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> obtenerComunaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(comunaService.obtenerComunaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ComunaDTO> crearComuna(@Valid @RequestBody ComunaDTO dto) {
        ComunaDTO creada = comunaService.crearComuna(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> actualizarComuna(
            @PathVariable Integer id,
            @Valid @RequestBody ComunaDTO dto) {
        ComunaDTO actualizada = comunaService.actualizarComuna(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComuna(@PathVariable Integer id) {
        comunaService.eliminarComuna(id);
        return ResponseEntity.noContent().build();
    }
}