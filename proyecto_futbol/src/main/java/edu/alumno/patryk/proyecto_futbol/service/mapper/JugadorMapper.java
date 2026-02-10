package edu.alumno.patryk.proyecto_futbol.service.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;

@Mapper
public interface JugadorMapper {
    JugadorMapper INSTANCE = Mappers.getMapper(JugadorMapper.class);


    JugadorDb jugadorEditToJugadorDb(JugadorEdit JugadorEdit);

    JugadorEdit jugadorDbToJugadorEdit(JugadorDb JugadorDb);

    JugadorList jugadorDbToJugadorList(JugadorDb JugadorDb);

    List<JugadorList> jugadoresDbToJugadorList(List<JugadorDb> jugadoresDb);


}
