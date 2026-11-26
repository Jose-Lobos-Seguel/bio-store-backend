package com.biostore.backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.ItemCarrito;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    Optional<ItemCarrito> findByCarritoIdAndProductoId(Integer carritoId, Integer productoId);
    List<ItemCarrito> findByCarritoId(Integer carritoId);
}