package edu.alumno.patryk.proyecto_futbol.service.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;
import edu.alumno.patryk.proyecto_futbol.model.db.JugadorEquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;

@Mapper
public interface JugadorMapper {
    JugadorMapper INSTANCE = Mappers.getMapper(JugadorMapper.class);


    JugadorDb jugadorEditToJugadorDb(JugadorEdit JugadorEdit);

    JugadorEdit jugadorDbToJugadorEdit(JugadorDb JugadorDb);

    JugadorList jugadorDbToJugadorList(JugadorDb JugadorDb);

    JugadorDb jugadorInfoToJugadorDb(JugadorInfo JugadorInfo);

    JugadorInfo jugadorDbToJugadorInfo(JugadorDb JugadorDb);

    @Mapping(source = "equipo.id", target = "idEquipo")
    JugadorInfo jugadorEquipoDbToJugadorInfo(JugadorEquipoDb jugadorEquipoDb);

    List<JugadorList> jugadoresDbToJugadorList(List<JugadorDb> jugadoresDb);

    /**
     * Convierte una página de jugadores en una respuesta paginada
     */
    static PaginaResponse<JugadorList> pageToPaginaResponse(
            Page<JugadorDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                JugadorMapper.INSTANCE.jugadoresDbToJugadorList(page.getContent()),
                filtros,
                ordenaciones);
    }

}
