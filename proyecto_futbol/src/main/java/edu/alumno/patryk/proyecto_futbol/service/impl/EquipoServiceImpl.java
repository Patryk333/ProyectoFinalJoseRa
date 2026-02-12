package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.List;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.EquipoNotFoundException;
import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.exception.IntegrityConstraintViolationException;
import edu.alumno.patryk.proyecto_futbol.helper.PaginationFactory;
import edu.alumno.patryk.proyecto_futbol.helper.PeticionListadoFiltradoConverter;
import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.PeticionListadoFiltrado;
import edu.alumno.patryk.proyecto_futbol.repository.EquipoRepository;
import edu.alumno.patryk.proyecto_futbol.service.EquipoService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.EquipoMapper;
import edu.alumno.patryk.proyecto_futbol.srv.specification.FiltroBusquedaSpecification;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final PaginationFactory paginationFactory;
    private final PeticionListadoFiltradoConverter peticionConverter;

    public EquipoServiceImpl(
            EquipoRepository equipoRepository,
            PaginationFactory paginationFactory,
            PeticionListadoFiltradoConverter peticionConverter) {
        this.equipoRepository = equipoRepository;
        this.paginationFactory = paginationFactory;
        this.peticionConverter = peticionConverter;
    }

    @Override
    public EquipoEdit save(EquipoEdit equipoEdit) {
        validarIdLiga(equipoEdit.getIdLiga());
        
        EquipoDb equipoDb = EquipoMapper.INSTANCE.equipoEditToEquipoDb(equipoEdit);
        equipoRepository.save(equipoDb);
        equipoEdit = EquipoMapper.INSTANCE.equipoDbToEquipoEdit(equipoDb);
        return equipoEdit;
    }

    @Override
    public EquipoInfo obtenerEquipoPorId(Long id) {
        EquipoDb equipoDb = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo_NOT_FOUND", "Equipo no encontrado con id: " + id));
        return (EquipoMapper.INSTANCE.equipoDbToEquipoInfo(equipoDb));
    }

    @Override
    public List<EquipoList> obtenerTodosEquipos() {
        List<EquipoDb> equipos = equipoRepository.findAll();
        return EquipoMapper.INSTANCE.equiposDbToEquipoList(equipos);
    }

    @Override
    public List<EquipoList> buscarEquipoPorNombre(String nombre) {
        List<EquipoDb> equipos = equipoRepository.findByNombreContainingIgnoreCase(nombre);
        return EquipoMapper.INSTANCE.equiposDbToEquipoList(equipos);
    }

    @Override
    public void eliminarEquipoPorId(Long id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
        } else {
            throw new EquipoNotFoundException("Equipo_NOT_FOUND", "Equipo no encontrado con id: " + id);
        }
    }

    @Override
    public EquipoEdit actualizarEquipo(Long id, EquipoEdit equipoEdit) {
        validarIdLiga(equipoEdit.getIdLiga());
        
        EquipoDb equipoBuscado = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo_NOT_FOUND", "Equipo no encontrado con id: " + id));
        
        equipoBuscado.setNombre(equipoEdit.getNombre());
        equipoBuscado.setEstadio(equipoEdit.getEstadio());
        equipoBuscado.setEntrenador(equipoEdit.getEntrenador());
        equipoBuscado.setCiudad(equipoEdit.getCiudad());
        equipoBuscado.setFundacion(equipoEdit.getFundacion());
        equipoBuscado.setIdLiga(equipoEdit.getIdLiga());
        equipoRepository.save(equipoBuscado);
        return EquipoMapper.INSTANCE.equipoDbToEquipoEdit(equipoBuscado);
    }

    @Override
    public PaginaResponse<EquipoList> findAll(String[] filter, int page, int size, String[] sort) throws FiltroException {
        PeticionListadoFiltrado peticion = peticionConverter.convertFromParams(filter, page, size, sort);
        return findAll(peticion);
    }

    @SuppressWarnings("null")
    @Override
    public PaginaResponse<EquipoList> findAll(PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        try {
            // Configurar ordenamiento
            Pageable pageable = paginationFactory.createPageable(peticionListadoFiltrado);
            // Configurar criterio de filtrado con Specification
            Specification<EquipoDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<EquipoDb>(
                    peticionListadoFiltrado.getListaFiltros());
            // Filtrar y ordenar
            Page<EquipoDb> page = equipoRepository.findAll(filtrosBusquedaSpecification, pageable);
            //Devolver respuesta
            return EquipoMapper.pageToPaginaResponse(
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
    
    private void validarIdLiga(Long idLiga) {
        if (idLiga == null || idLiga <= 0) {
            throw new IntegrityConstraintViolationException("FOREIGN_KEY_VIOLATION", 
                "El id de la liga debe ser válido y mayor que 0");
        }
    }

}
