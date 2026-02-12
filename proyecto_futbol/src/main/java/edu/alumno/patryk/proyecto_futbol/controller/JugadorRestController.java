package edu.alumno.patryk.proyecto_futbol.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.PeticionListadoFiltrado;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/futbol")
public class JugadorRestController {
    
    private final JugadorService jugadorService;

    public JugadorRestController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping("/jugadores/crear")
    public JugadorEdit creaJugadorEdit(@Valid @RequestBody JugadorEdit jugadorEdit) {
        return jugadorService.save(jugadorEdit);
    }
    
    @GetMapping("/jugadores/{id}")
    public JugadorInfo obtenerJugadorPorId(@PathVariable Long id) {
        return jugadorService.obtenerJugadorPorId(id);
    }
    
    @GetMapping("/jugadores")
    public List<JugadorList> obtenerTodosJugadores() {
        return jugadorService.obtenerTodosJugadores();
    }
    
    @GetMapping("/jugadores/buscar/nombre")
    public List<JugadorList> buscarJugadorPorNombre(@RequestParam String nombre) {
        return jugadorService.buscarJugadorPorNombre(nombre);
    }

    @GetMapping("/jugadores/filtrado")
    public ResponseEntity<PaginaResponse<JugadorList>> getAllJugadoresFiltrado(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) throws FiltroException {
        return ResponseEntity.ok(jugadorService.findAll(filter, page, size, sort));
    }

    @PostMapping("/jugadores/filtrado")
    public ResponseEntity<PaginaResponse<JugadorList>> getAllJugadoresPost(
            @Valid @RequestBody PeticionListadoFiltrado peticionListadoFiltrado) throws FiltroException {
        return ResponseEntity.ok(jugadorService.findAll(peticionListadoFiltrado));
    }
    
    @DeleteMapping("/jugadores/{id}")
    public ResponseEntity<Void> eliminarJugadorPorId(@PathVariable Long id) {
        jugadorService.eliminarJugadorPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/jugadores/{id}")
    public JugadorEdit actualizarJugador(@PathVariable Long id, @Valid @RequestBody JugadorEdit jugadorEdit) {
        return jugadorService.actualizarJugador(id, jugadorEdit);
    }
    
}
