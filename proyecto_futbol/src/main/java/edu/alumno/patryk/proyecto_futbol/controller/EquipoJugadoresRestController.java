package edu.alumno.patryk.proyecto_futbol.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoConJugadoresDto;
import edu.alumno.patryk.proyecto_futbol.service.EquipoJugadoresService;

@RestController
@RequestMapping("/api/futbol")
public class EquipoJugadoresRestController {

    private final EquipoJugadoresService equipoJugadoresService;

    public EquipoJugadoresRestController(EquipoJugadoresService equipoJugadoresService) {
        this.equipoJugadoresService = equipoJugadoresService;
    }

    @GetMapping("/equipos/con-jugadores")
    public ResponseEntity<List<EquipoConJugadoresDto>> getEquiposConJugadores() {
        return ResponseEntity.ok(equipoJugadoresService.getEquiposConJugadores());
    }

    @GetMapping("/equipos/{id}/jugadores")
    public ResponseEntity<EquipoConJugadoresDto> getEquipoConJugadores(@PathVariable Long id) {
        return ResponseEntity.ok(equipoJugadoresService.getEquipoConJugadores(id));
    }
}
