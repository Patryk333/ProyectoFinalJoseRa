package edu.alumno.patryk.proyecto_futbol.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasPorNacionalidadDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasPorPosicionDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;

@Service
public interface JugadorService {
    
    public JugadorEdit save(JugadorEdit jugadorEdit);
    
    public JugadorInfo obtenerJugadorPorId(Long id);
    
    public List<JugadorList> obtenerTodosJugadores();
    
    public List<JugadorList> buscarJugadorPorNombre(String nombre);
    
    public void eliminarJugadorPorId(Long id);
    
    public JugadorEdit actualizarJugador(Long id, JugadorEdit jugadorEdit);

    public PaginaResponse<JugadorList> findAllPageJugadorList(List<FiltroBusqueda> listaFiltros, Pageable pageable);

    // Métodos para consultas agregadas
    public List<EstadisticasPorPosicionDto> obtenerEstadisticasPorPosicion();
    
    public List<EstadisticasPorNacionalidadDto> obtenerEstadisticasPorNacionalidad();
    
    public Long obtenerTotalJugadores();
    
    public Double obtenerPromedioJugadoresPorEquipo();

}