package com.biostore.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Usuario;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}
