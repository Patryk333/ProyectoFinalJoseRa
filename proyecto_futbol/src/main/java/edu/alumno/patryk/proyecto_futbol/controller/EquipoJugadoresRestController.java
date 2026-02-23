package edu.alumno.patryk.proyecto_futbol.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoConJugadoresDto;
import edu.alumno.patryk.proyecto_futbol.service.EquipoJugadoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/futbol")
@Tag(name = "Equipos y Jugadores", description = "Operaciones para obtener la relación entre equipos y sus jugadores asociados. Estos endpoints son públicos y no requieren autenticación.")
public class EquipoJugadoresRestController {

    private final EquipoJugadoresService equipoJugadoresService;

    public EquipoJugadoresRestController(EquipoJugadoresService equipoJugadoresService) {
        this.equipoJugadoresService = equipoJugadoresService;
    }

    @GetMapping("/equipos/con-jugadores")
    @Operation(summary = "Obtener todos los equipos con sus jugadores", description = "Recupera la lista completa de todos los equipos junto con la información detallada de todos sus jugadores asociados. Útil para obtener la estructura completa de los equipos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos con jugadores obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay equipos registrados en el sistema")
    })
    public ResponseEntity<List<EquipoConJugadoresDto>> getEquiposConJugadores() {
        return ResponseEntity.ok(equipoJugadoresService.getEquiposConJugadores());
    }

    @GetMapping("/equipos/{id}/jugadores")
    @Operation(summary = "Obtener equipo con sus jugadores", description = "Recupera un equipo específico junto con la información detallada de todos sus jugadores asociados. Permite ver la composición del equipo de una forma estructurada.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo con jugadores obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado con el ID especificado")
    })
    public ResponseEntity<EquipoConJugadoresDto> getEquipoConJugadores(@PathVariable Long id) {
        return ResponseEntity.ok(equipoJugadoresService.getEquipoConJugadores(id));
    }
}
