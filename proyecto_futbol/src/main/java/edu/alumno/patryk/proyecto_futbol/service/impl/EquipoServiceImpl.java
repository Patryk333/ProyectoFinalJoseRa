package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.EquipoNotFoundException;
import edu.alumno.patryk.proyecto_futbol.exception.IntegrityConstraintViolationException;
import edu.alumno.patryk.proyecto_futbol.model.db.EquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasEquipoDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasGeneralesDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasLigaDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.repository.EquipoRepository;
import edu.alumno.patryk.proyecto_futbol.repository.JugadorRepository;
import edu.alumno.patryk.proyecto_futbol.service.EquipoService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.EquipoMapper;
import edu.alumno.patryk.proyecto_futbol.srv.specification.FiltroBusquedaSpecification;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final JugadorRepository jugadorRepository;

    public EquipoServiceImpl(EquipoRepository equipoRepository, JugadorRepository jugadorRepository) {
        this.equipoRepository = equipoRepository;
        this.jugadorRepository = jugadorRepository;
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
    public PaginaResponse<EquipoList> findAllPageEquipoList(List<FiltroBusqueda> listaFiltros, Pageable pageable) {
        Page<EquipoDb> paginaEquipoDb;
        
        if(listaFiltros.isEmpty()) {
            paginaEquipoDb = equipoRepository.findAll(pageable);
        } else {
            Specification<EquipoDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(listaFiltros);
            paginaEquipoDb = equipoRepository.findAll(filtrosBusquedaSpecification, pageable);
        }
        
        return new PaginaResponse<>(
            paginaEquipoDb.getNumber(),
            paginaEquipoDb.getSize(),
            paginaEquipoDb.getTotalElements(),
            paginaEquipoDb.getTotalPages(),
            EquipoMapper.INSTANCE.equiposDbToEquipoList(paginaEquipoDb.getContent()),
            listaFiltros,
            paginaEquipoDb.getSort().stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
                .toList()
        );
    }
    
    @Override
    public EstadisticasGeneralesDto obtenerEstadisticasGenerales() {
        Long totalEquipos = equipoRepository.count();
        Long totalJugadores = jugadorRepository.count();
        Long totalLigas = equipoRepository.countDistinctLigas();
        Double promedioJugadores = totalEquipos > 0 ? totalJugadores.doubleValue() / totalEquipos.doubleValue() : 0.0;
        
        return new EstadisticasGeneralesDto(totalEquipos, totalJugadores, totalLigas, promedioJugadores);
    }
    
    @Override
    public List<EstadisticasEquipoDto> obtenerEstadisticasEquipos() {
        List<Object[]> resultados = equipoRepository.estadisticasJugadoresPorEquipo();
        return resultados.stream()
            .map(obj -> new EstadisticasEquipoDto(
                (Long) obj[0],
                (String) obj[1],
                ((Number) obj[2]).longValue(),
                (String) obj[3]
            ))
            .toList();
    }
    
    @Override
    public List<EstadisticasLigaDto> obtenerEstadisticasPorLiga() {
        List<Object[]> resultados = equipoRepository.countEquiposPorLiga();
        return resultados.stream()
            .map(obj -> {
                Long idLiga = (Long) obj[0];
                Long cantidadEquipos = (Long) obj[1];
                Long totalJugadores = equipoRepository.findAll().stream()
                    .filter(e -> e.getIdLiga().equals(idLiga))
                    .mapToLong(e -> jugadorRepository.count())
                    .sum();
                
                return new EstadisticasLigaDto(idLiga, cantidadEquipos, totalJugadores);
            })
            .toList();
    }
    
    @Override
    public Long obtenerTotalEquipos() {
        return equipoRepository.count();
    }
    
    private void validarIdLiga(Long idLiga) {
        if (idLiga == null || idLiga <= 0) {
            throw new IntegrityConstraintViolationException("FOREIGN_KEY_VIOLATION", 
                "El id de la liga debe ser válido y mayor que 0");
        }
    }

}
