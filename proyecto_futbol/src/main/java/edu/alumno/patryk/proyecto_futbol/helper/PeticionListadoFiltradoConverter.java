package edu.alumno.patryk.proyecto_futbol.helper;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PeticionListadoFiltrado;

@Component
public class PeticionListadoFiltradoConverter {
    private final FiltroBusquedaFactory filtroBusquedaFactory;

    public PeticionListadoFiltradoConverter(FiltroBusquedaFactory filtroBusquedaFactory) {
        this.filtroBusquedaFactory = filtroBusquedaFactory;
    }

    public PeticionListadoFiltrado convertFromParams(
            String[] filter,
            int page,
            int size,
            String[] sort) throws FiltroException {
        List<FiltroBusqueda> filtros = filtroBusquedaFactory.crearListaFiltrosBusqueda(filter);
        
        return new PeticionListadoFiltrado(filtros, page, size, sort);
    }
}
