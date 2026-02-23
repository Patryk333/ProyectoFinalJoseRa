package edu.alumno.patryk.proyecto_futbol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.alumno.patryk.proyecto_futbol.model.db.DocEquipoDb;

@Repository
public interface DocEquipoRepository extends JpaRepository<DocEquipoDb, Long> {
}
