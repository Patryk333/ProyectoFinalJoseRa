package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.exception.IntegrityConstraintViolationException;
import edu.alumno.patryk.proyecto_futbol.exception.JugadorNotFoundException;
import edu.alumno.patryk.proyecto_futbol.helper.PaginationFactory;
import edu.alumno.patryk.proyecto_futbol.helper.PeticionListadoFiltradoConverter;
import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.PeticionListadoFiltrado;
import edu.alumno.patryk.proyecto_futbol.repository.JugadorRepository;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.JugadorMapper;
import edu.alumno.patryk.proyecto_futbol.srv.specification.FiltroBusquedaSpecification;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    public JugadorServiceImpl(
            JugadorRepository jugadorRepository,
            PaginationFactory paginationFactory,
            PeticionListadoFiltradoConverter peticionConverter) {
        this.jugadorRepository = jugadorRepository;
        this.paginationFactory = paginationFactory;
        this.peticionConverter = peticionConverter;
    }

    @Override
    public JugadorEdit save(JugadorEdit jugadorEdit) {
        validarIdEquipo(jugadorEdit.getIdEquipo());
        
        JugadorDb jugadorDb = JugadorMapper.INSTANCE.jugadorEditToJugadorDb(jugadorEdit);
        jugadorRepository.save(jugadorDb);
        jugadorEdit = JugadorMapper.INSTANCE.jugadorDbToJugadorEdit(jugadorDb);
        return jugadorEdit;
    }

    @Override
    public JugadorInfo obtenerJugadorPorId(Long id) {
        JugadorDb jugadorDb = jugadorRepository.findById(id)
                .orElseThrow(() -> new JugadorNotFoundException("Jugador_NOT_FOUND", "Jugador no encontrado con id: " + id));
        return (JugadorMapper.INSTANCE.jugadorDbToJugadorInfo(jugadorDb));
    }

    @Override
    public List<JugadorList> obtenerTodosJugadores() {
        List<JugadorDb> jugadores = jugadorRepository.findAll();
        return JugadorMapper.INSTANCE.jugadoresDbToJugadorList(jugadores);
    }

    @Override
    public List<JugadorList> buscarJugadorPorNombre(String nombre) {
        List<JugadorDb> jugadores = jugadorRepository.findByNombreContainingIgnoreCase(nombre);
        return JugadorMapper.INSTANCE.jugadoresDbToJugadorList(jugadores);
    }

    @Override
    public void eliminarJugadorPorId(Long id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
        } else {
            throw new JugadorNotFoundException("Jugador_NOT_FOUND", "Jugador no encontrado con id: " + id);
        }
    }

    @Override
    public JugadorEdit actualizarJugador(Long id, JugadorEdit jugadorEdit) {
        validarIdEquipo(jugadorEdit.getIdEquipo());
        
        JugadorDb jugadorBuscado = jugadorRepository.findById(id)
                .orElseThrow(() -> new JugadorNotFoundException("Jugador_NOT_FOUND", "Jugador no encontrado con id: " + id));
        
        jugadorBuscado.setNombre(jugadorEdit.getNombre());
        jugadorBuscado.setDorsal(jugadorEdit.getDorsal());
        jugadorBuscado.setPosicion(jugadorEdit.getPosicion());
        jugadorBuscado.setNacionalidad(jugadorEdit.getNacionalidad());
        jugadorBuscado.setFechaNacimiento(jugadorEdit.getFechaNacimiento());
        jugadorBuscado.setIdEquipo(jugadorEdit.getIdEquipo());
        jugadorRepository.save(jugadorBuscado);
        return JugadorMapper.INSTANCE.jugadorDbToJugadorEdit(jugadorBuscado);
    }

    @Override
    public PaginaResponse<JugadorList> findAll(String[] filter, int page, int size, String[] sort) throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<JugadorList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            // Configurar ordenamiento
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<JugadorDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<JugadorDb>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar
            Page<JugadorDb> page = jugadorRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return JugadorMapper.pageToPaginaResponse(
                page,
                peticionListadoFiltrado.getListaFiltros(), 
                peticionListadoFiltrado.getSort());
        } catch (JpaSystemException e) {
            String cause = "";
            if (e.getRootCause() != null) {
                if (e.getCause().getMessage() != null)
                    cause = e.getRootCause().getMessage();
            }
            throw new FiltroException("BAD_OPERATOR_FILTER",
                    "Error: No se puede realizar esa operación sobre el atributo por el tipo de dato", e.getMessage() + ":" + cause);
        } catch (PropertyReferenceException e) {
            throw new FiltroException("BAD_ATTRIBUTE_ORDER",
                    "Error: No existe el nombre del atributo de ordenación en la tabla", e.getMessage());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new FiltroException("BAD_ATTRIBUTE_FILTER", "Error: Posiblemente no existe el atributo en la tabla",
                    e.getMessage());
        }
    }
    
    private void validarIdEquipo(Long idEquipo) {
        if (idEquipo == null || idEquipo <= 0) {
            throw new IntegrityConstraintViolationException("FOREIGN_KEY_VIOLATION", 
                "El id del equipo debe ser válido y mayor que 0");
        }
    }

}
