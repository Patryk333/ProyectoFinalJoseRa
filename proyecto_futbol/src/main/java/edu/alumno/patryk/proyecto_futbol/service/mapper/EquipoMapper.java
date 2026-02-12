package edu.alumno.patryk.proyecto_futbol.service.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;

@Mapper
public interface EquipoMapper {
    EquipoMapper INSTANCE = Mappers.getMapper(EquipoMapper.class);


    EquipoDb equipoEditToEquipoDb(EquipoEdit equipoEdit);

    EquipoEdit equipoDbToEquipoEdit(EquipoDb equipoDb);

    EquipoList equipoDbToEquipoList(EquipoDb equipoDb);

    EquipoDb equipoInfoToEquipoDb(EquipoInfo equipoInfo);

    EquipoInfo equipoDbToEquipoInfo(EquipoDb equipoDb);

    List<EquipoList> equiposDbToEquipoList(List<EquipoDb> equiposDb);

    /**
     * Convierte una página de equipos en una respuesta paginada
     */
    static PaginaResponse<EquipoList> pageToPaginaResponse(
            Page<EquipoDb> page,
            List<FiltroBusqueda> filtros,
            List<String> ordenaciones) {
        return new PaginaResponse<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                EquipoMapper.INSTANCE.equiposDbToEquipoList(page.getContent()),
                filtros,
                ordenaciones);
    }

}
