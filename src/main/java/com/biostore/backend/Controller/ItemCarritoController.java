package com.biostore.backend.Controller;

import com.biostore.backend.DTO.ItemCarritoDTO;
import com.biostore.backend.Service.ItemCarritoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items-carrito")
@RequiredArgsConstructor
public class ItemCarritoController {

    private final ItemCarritoService itemCarritoService;

    @GetMapping("/carrito/{carritoId}")
    public ResponseEntity<List<ItemCarritoDTO>> listarPorCarrito(@PathVariable Integer carritoId) {
        return ResponseEntity.ok(itemCarritoService.listarPorCarrito(carritoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemCarritoDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(itemCarritoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemCarritoDTO> guardarItem(@RequestBody ItemCarritoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemCarritoService.guardarItem(dto));
    }

    @PatchMapping("/{id}/cantidad")
    public ResponseEntity<ItemCarritoDTO> actualizarCantidad(
            @PathVariable Integer id,
            @RequestParam Integer cantidad) {
        ItemCarritoDTO actualizado = itemCarritoService.actualizarCantidad(id, cantidad);
        if (actualizado == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Integer id) {
        itemCarritoService.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}