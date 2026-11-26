package com.biostore.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Imagen;

public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findByProductoId(Integer productoId);
}
