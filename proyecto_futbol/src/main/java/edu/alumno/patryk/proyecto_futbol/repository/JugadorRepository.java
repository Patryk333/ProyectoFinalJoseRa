package edu.alumno.patryk.proyecto_futbol.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;

public interface JugadorRepository extends JpaRepository<JugadorDb, Long>{
    
}
