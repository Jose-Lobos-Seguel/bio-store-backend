package com.biostore.backend.Controller;

import com.biostore.backend.DTO.CarritoDTO;
import com.biostore.backend.DTO.ItemCarritoDTO;
import com.biostore.backend.Service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<CarritoDTO> obtenerCarritoPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(usuarioId));
    }

    @PostMapping("/usuario/{usuarioId}/items")
    public ResponseEntity<CarritoDTO> agregarItem(
            @PathVariable Integer usuarioId,
            @RequestBody ItemCarritoDTO itemCarritoDTO) {
        return ResponseEntity.ok(carritoService.agregarItem(usuarioId, itemCarritoDTO));
    }

    @DeleteMapping("/usuario/{usuarioId}/items/{itemId}")
    public ResponseEntity<CarritoDTO> eliminarItem(
            @PathVariable Integer usuarioId,
            @PathVariable Integer itemId) {
        return ResponseEntity.ok(carritoService.eliminarItem(usuarioId, itemId));
    }

    @DeleteMapping("/usuario/{usuarioId}/vaciar")
    public ResponseEntity<CarritoDTO> vaciarCarrito(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(carritoService.vaciarCarrito(usuarioId));
    }
}