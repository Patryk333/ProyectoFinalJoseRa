package edu.alumno.patryk.proyecto_futbol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import edu.alumno.patryk.proyecto_futbol.model.db.UsuarioDb;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDb, Long> {
    Optional<UsuarioDb> findByUsername(String username);
    Optional<UsuarioDb> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
