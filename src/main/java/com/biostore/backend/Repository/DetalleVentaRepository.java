package com.biostore.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer>{
    List<DetalleVenta> findByVentaId(Integer ventaId);    
}