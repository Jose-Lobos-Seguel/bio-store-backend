package com.biostore.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByFranquiciaId(Integer franquiciaId);

    
}