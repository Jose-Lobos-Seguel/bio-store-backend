package com.biostore.backend.Repository;

import com.biostore.backend.Model.Direccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByUsuarioId(Integer usuarioId);
}