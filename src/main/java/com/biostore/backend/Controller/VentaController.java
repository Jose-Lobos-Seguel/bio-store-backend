package com.biostore.backend.Controller;

import com.biostore.backend.DTO.VentaDTO;
import com.biostore.backend.Service.VentaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> listarVentas() {
        return ResponseEntity.ok(ventaService.listarVentas());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<VentaDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(ventaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> obtenerVentaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.obtenerVentaPorId(id));
    }

    @PostMapping("/comprar/{usuarioId}")
    public ResponseEntity<VentaDTO> realizarCompraDesdeCarrito(
            @PathVariable Integer usuarioId,
            @RequestParam(required = false) Integer direccionId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.realizarCompraDesdeCarrito(usuarioId, direccionId));
    }
}