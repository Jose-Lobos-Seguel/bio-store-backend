package com.biostore.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Carrito;

public interface CarritoRepository  extends JpaRepository<Carrito, Integer>{
    Optional<Carrito> findByUsuarioId(Integer usuarioId);
}