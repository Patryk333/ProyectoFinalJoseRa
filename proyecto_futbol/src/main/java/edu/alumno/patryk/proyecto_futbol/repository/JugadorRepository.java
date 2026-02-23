package edu.alumno.patryk.proyecto_futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;

public interface JugadorRepository extends JpaRepository<JugadorDb, Long>, JpaSpecificationExecutor<JugadorDb>{
    
    List<JugadorDb> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT COUNT(DISTINCT j.posicion) FROM JugadorDb j")
    Long countDistinctPosiciones();
    
    @Query("SELECT COUNT(DISTINCT j.nacionalidad) FROM JugadorDb j")
    Long countDistinctNacionalidades();
    
    @Query("SELECT COUNT(DISTINCT j.idEquipo) FROM JugadorDb j")
    Long countEquiposConJugadores();
    
    @Query("SELECT j.posicion as posicion, COUNT(j) as cantidad FROM JugadorDb j GROUP BY j.posicion")
    List<Object[]> countJugadoresPorPosicion();
    
    @Query("SELECT j.nacionalidad as nacionalidad, COUNT(j) as cantidad FROM JugadorDb j GROUP BY j.nacionalidad")
    List<Object[]> countJugadoresPorNacionalidad();
    
}
