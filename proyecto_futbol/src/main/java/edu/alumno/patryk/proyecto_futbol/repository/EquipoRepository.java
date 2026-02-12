package edu.alumno.patryk.proyecto_futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;

public interface EquipoRepository extends JpaRepository<EquipoDb, Long>, JpaSpecificationExecutor<EquipoDb>{
    
    List<EquipoDb> findByNombreContainingIgnoreCase(String nombre);
    
}
