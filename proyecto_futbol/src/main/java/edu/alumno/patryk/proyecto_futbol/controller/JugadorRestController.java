package edu.alumno.patryk.proyecto_futbol.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/futbol")
public class JugadorRestController {
    
    private final JugadorService jugadorService;

    public JugadorRestController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping("/jugadores/crear")
    public JugadorEdit creaJugadorEdit(@Valid @RequestBody JugadorEdit jugadorEdit) {
        return jugadorService.save(jugadorEdit);
    }
    
}
