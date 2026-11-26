package com.biostore.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByUsuarioId(Integer usuarioId);
}