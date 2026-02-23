package edu.alumno.patryk.proyecto_futbol.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;

public interface EquipoRepository extends JpaRepository<EquipoDb, Long>, JpaSpecificationExecutor<EquipoDb>{
    
    List<EquipoDb> findByNombreContainingIgnoreCase(String nombre);
    
    // Consultas agregadas
    @Query("SELECT COUNT(DISTINCT e.idLiga) FROM EquipoDb e")
    Long countDistinctLigas();
    
    @Query("SELECT e.id as idEquipo, e.nombre as nombreEquipo, COUNT(je.id) as cantidadJugadores, e.ciudad FROM EquipoDb e LEFT JOIN JugadorEquipoDb je ON e.id = je.equipo.id GROUP BY e.id, e.nombre, e.ciudad")
    List<Object[]> estadisticasJugadoresPorEquipo();
    
    @Query("SELECT e.idLiga as idLiga, COUNT(e) as cantidadEquipos FROM EquipoDb e GROUP BY e.idLiga")
    List<Object[]> countEquiposPorLiga();
    
}
