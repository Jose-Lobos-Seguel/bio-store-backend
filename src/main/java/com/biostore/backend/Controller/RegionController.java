package com.biostore.backend.Controller;

import com.biostore.backend.DTO.RegionDTO;
import com.biostore.backend.Service.RegionService;
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
@RequestMapping("/api/regiones")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDTO>> listarRegiones() {
        return ResponseEntity.ok(regionService.listarRegiones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> obtenerRegionPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(regionService.obtenerRegionPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegionDTO> crearRegion(@Valid @RequestBody RegionDTO dto) {
        RegionDTO creada = regionService.crearRegion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> actualizarRegion(
            @PathVariable Integer id,
            @Valid @RequestBody RegionDTO dto) {
        RegionDTO actualizada = regionService.actualizarRegion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable Integer id) {
        regionService.eliminarRegion(id);
        return ResponseEntity.noContent().build();
    }
}