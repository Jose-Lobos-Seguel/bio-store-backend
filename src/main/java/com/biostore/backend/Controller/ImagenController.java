package com.biostore.backend.Controller;

import com.biostore.backend.DTO.ImagenDTO;
import com.biostore.backend.Service.ImagenService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenService imagenService;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ImagenDTO>> listarPorProducto(@PathVariable Integer productoId) {
        return ResponseEntity.ok(imagenService.listarPorProducto(productoId));
    }

    @PostMapping
    public ResponseEntity<ImagenDTO> crearImagen(@RequestBody ImagenDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenService.crearImagen(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarImagen(@PathVariable Integer id) {
        imagenService.eliminarImagen(id);
        return ResponseEntity.noContent().build();
    }
}