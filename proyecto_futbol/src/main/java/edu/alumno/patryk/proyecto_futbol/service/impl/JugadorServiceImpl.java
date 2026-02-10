package edu.alumno.patryk.proyecto_futbol.service.impl;

import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.model.db.JugadorDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.repository.JugadorRepository;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.JugadorMapper;

@Service
public class JugadorServiceImpl implements JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorServiceImpl(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public JugadorEdit save(JugadorEdit jugadorEdit) {
        JugadorDb jugadorDb = JugadorMapper.INSTANCE.jugadorEditToJugadorDb(jugadorEdit);
        jugadorRepository.save(jugadorDb);
        jugadorEdit = JugadorMapper.INSTANCE.jugadorDbToJugadorEdit(jugadorDb);
        return jugadorEdit;
    }
}
