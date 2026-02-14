package edu.alumno.patryk.proyecto_futbol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoConJugadoresDto;

@Service
public interface EquipoJugadoresService {
    List<EquipoConJugadoresDto> getEquiposConJugadores();
    EquipoConJugadoresDto getEquipoConJugadores(Long idEquipo);
}
