package edu.alumno.patryk.proyecto_futbol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.PeticionListadoFiltrado;

@Service
public interface EquipoService {
    
    public EquipoEdit save(EquipoEdit equipoEdit);
    
    public EquipoInfo obtenerEquipoPorId(Long id);
    
    public List<EquipoList> obtenerTodosEquipos();
    
    public List<EquipoList> buscarEquipoPorNombre(String nombre);
    
    public void eliminarEquipoPorId(Long id);
    
    public EquipoEdit actualizarEquipo(Long id, EquipoEdit equipoEdit);

    public PaginaResponse<EquipoList> findAll(String[] filter, int page, int size, String[] sort) throws FiltroException;

    public PaginaResponse<EquipoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException;

}
