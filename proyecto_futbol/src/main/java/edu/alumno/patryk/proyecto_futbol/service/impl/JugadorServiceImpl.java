package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.IntegrityConstraintViolationException;
import edu.alumno.patryk.proyecto_futbol.exception.JugadorNotFoundException;
import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.repository.JugadorRepository;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.JugadorMapper;
import edu.alumno.patryk.proyecto_futbol.srv.specification.FiltroBusquedaSpecification;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorServiceImpl(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
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
    public PaginaResponse<JugadorList> findAllPageJugadorList(List<FiltroBusqueda> listaFiltros, Pageable pageable) {
        Page<JugadorDb> paginaJugadorDb;
        
        if(listaFiltros.isEmpty()) {
            paginaJugadorDb = jugadorRepository.findAll(pageable);
        } else {
            Specification<JugadorDb> filtrosBusquedaSpecification = new FiltroBusquedaSpecification<>(listaFiltros);
            paginaJugadorDb = jugadorRepository.findAll(filtrosBusquedaSpecification, pageable);
        }
        
        return new PaginaResponse<>(
            paginaJugadorDb.getNumber(),
            paginaJugadorDb.getSize(),
            paginaJugadorDb.getTotalElements(),
            paginaJugadorDb.getTotalPages(),
            JugadorMapper.INSTANCE.jugadoresDbToJugadorList(paginaJugadorDb.getContent()),
            listaFiltros,
            paginaJugadorDb.getSort().stream()
                .map(order -> order.getProperty() + "," + order.getDirection().name().toLowerCase())
                .toList()
        );
    }
    
    private void validarIdEquipo(Long idEquipo) {
        if (idEquipo == null || idEquipo <= 0) {
            throw new IntegrityConstraintViolationException("FOREIGN_KEY_VIOLATION", 
                "El id del equipo debe ser válido y mayor que 0");
        }
    }

}
