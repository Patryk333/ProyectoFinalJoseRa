package edu.alumno.patryk.proyecto_futbol.service;

import java.util.List;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;

@Service
public interface JugadorService {
    
    public JugadorEdit save(JugadorEdit jugadorEdit);
    
    public JugadorInfo obtenerJugadorPorId(Long id);
    
    public List<JugadorList> obtenerTodosJugadores();
    
    public List<JugadorList> buscarJugadorPorNombre(String nombre);
    
    public void eliminarJugadorPorId(Long id);
    
    public JugadorEdit actualizarJugador(Long id, JugadorEdit jugadorEdit);

}