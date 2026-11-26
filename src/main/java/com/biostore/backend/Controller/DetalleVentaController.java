package com.biostore.backend.Controller;

import com.biostore.backend.DTO.DetalleVentaDTO;
import com.biostore.backend.Service.DetalleVentaService;
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
@RequestMapping("/api/detalles-venta")
@RequiredArgsConstructor
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    @GetMapping("/venta/{ventaId}")
    public ResponseEntity<List<DetalleVentaDTO>> listarPorVenta(@PathVariable Integer ventaId) {
        return ResponseEntity.ok(detalleVentaService.listarPorVenta(ventaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(detalleVentaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<DetalleVentaDTO> crearDetalle(@RequestBody DetalleVentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detalleVentaService.crearDetalle(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer id) {
        detalleVentaService.eliminarDetalle(id);
        return ResponseEntity.noContent().build();
    }
}