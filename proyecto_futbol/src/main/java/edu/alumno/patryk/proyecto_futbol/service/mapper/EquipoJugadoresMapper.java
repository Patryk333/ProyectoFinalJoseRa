package edu.alumno.patryk.proyecto_futbol.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoConJugadoresDto;

@Mapper(uses = {JugadorMapper.class})
public interface EquipoJugadoresMapper {
    EquipoJugadoresMapper INSTANCE = Mappers.getMapper(EquipoJugadoresMapper.class);

    @Mapping(target = "jugadores", source = "jugadoresEquipoDb")
    EquipoConJugadoresDto equipoDbToEquipoConJugadoresDto(EquipoDb equipoDb);
}
