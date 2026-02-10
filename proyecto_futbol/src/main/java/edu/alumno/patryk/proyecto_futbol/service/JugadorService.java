package edu.alumno.patryk.proyecto_futbol.service;

import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;

@Service
public interface JugadorService {
    
    public JugadorEdit save(JugadorEdit jugadorEdit);

}