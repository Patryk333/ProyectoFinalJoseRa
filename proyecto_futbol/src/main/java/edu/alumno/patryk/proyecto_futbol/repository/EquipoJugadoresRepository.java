package edu.alumno.patryk.proyecto_futbol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;

public interface EquipoJugadoresRepository extends JpaRepository<EquipoDb, Long> {

    @EntityGraph(attributePaths = "jugadoresEquipoDb")
    List<EquipoDb> findAll();

    @EntityGraph(attributePaths = "jugadoresEquipoDb")
    Optional<EquipoDb> findById(Long id);
}
