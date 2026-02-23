package edu.alumno.patryk.proyecto_futbol.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasEquipoDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasGeneralesDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasLigaDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;

@Service
public interface EquipoService {
    
    public EquipoEdit save(EquipoEdit equipoEdit);
    
    public EquipoInfo obtenerEquipoPorId(Long id);
    
    public List<EquipoList> obtenerTodosEquipos();
    
    public List<EquipoList> buscarEquipoPorNombre(String nombre);
    
    public void eliminarEquipoPorId(Long id);
    
    public EquipoEdit actualizarEquipo(Long id, EquipoEdit equipoEdit);

    public PaginaResponse<EquipoList> findAllPageEquipoList(List<FiltroBusqueda> listaFiltros, Pageable pageable);

    // Métodos para consultas agregadas
    public EstadisticasGeneralesDto obtenerEstadisticasGenerales();
    
    public List<EstadisticasEquipoDto> obtenerEstadisticasEquipos();
    
    public List<EstadisticasLigaDto> obtenerEstadisticasPorLiga();
    
    public Long obtenerTotalEquipos();

}
