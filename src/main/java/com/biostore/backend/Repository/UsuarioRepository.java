package com.biostore.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.biostore.backend.Model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
