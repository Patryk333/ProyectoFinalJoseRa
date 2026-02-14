package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.EquipoNotFoundException;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoConJugadoresDto;
import edu.alumno.patryk.proyecto_futbol.repository.EquipoJugadoresRepository;
import edu.alumno.patryk.proyecto_futbol.service.EquipoJugadoresService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.EquipoJugadoresMapper;

@Service
public class EquipoJugadoresServiceImpl implements EquipoJugadoresService {

    private final EquipoJugadoresRepository equipoJugadoresRepository;

    public EquipoJugadoresServiceImpl(EquipoJugadoresRepository equipoJugadoresRepository) {
        this.equipoJugadoresRepository = equipoJugadoresRepository;
    }

    @Override
    public List<EquipoConJugadoresDto> getEquiposConJugadores() {
        return equipoJugadoresRepository.findAll().stream()
            .map(EquipoJugadoresMapper.INSTANCE::equipoDbToEquipoConJugadoresDto)
            .collect(Collectors.toList());
    }

    @Override
    public EquipoConJugadoresDto getEquipoConJugadores(Long idEquipo) {
        return equipoJugadoresRepository.findById(idEquipo)
            .map(EquipoJugadoresMapper.INSTANCE::equipoDbToEquipoConJugadoresDto)
            .orElseThrow(() -> new EquipoNotFoundException(
                "Equipo_NOT_FOUND",
                "Equipo no encontrado con id: " + idEquipo));
    }
}
