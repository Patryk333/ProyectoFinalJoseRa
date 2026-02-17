package edu.alumno.patryk.proyecto_futbol.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
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

import edu.alumno.patryk.proyecto_futbol.helper.FiltroBusquedaHelper;
import edu.alumno.patryk.proyecto_futbol.helper.PaginationHelper;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.JugadorList;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.service.JugadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/futbol")
@Tag(name = "Jugadores", description = "Operaciones CRUD para gestionar jugadores de fútbol. Estos endpoints son públicos y no requieren autenticación.")
public class JugadorRestController {

    private final JugadorService jugadorService;

    public JugadorRestController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping("/jugadores/crear")
    @Operation(summary = "Crear nuevo jugador", description = "Crea un nuevo jugador asociado a un equipo. El dorsal debe estar entre 1 y 99.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jugador creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Validación fallida - Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Conflicto - Equipo no existe")
    })
    public JugadorEdit creaJugadorEdit(@Valid @RequestBody JugadorEdit jugadorEdit) {
        return jugadorService.save(jugadorEdit);
    }
    
    @GetMapping("/jugadores/{id}")
    @Operation(summary = "Obtener jugador por ID", description = "Retorna los detalles completos de un jugador específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jugador encontrado"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public JugadorInfo obtenerJugadorPorId(@PathVariable Long id) {
        return jugadorService.obtenerJugadorPorId(id);
    }
    
    @GetMapping("/jugadores")
    @Operation(summary = "Listar todos los jugadores", description = "Retorna una lista con todos los jugadores registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida")
    public List<JugadorList> obtenerTodosJugadores() {
        return jugadorService.obtenerTodosJugadores();
    }
    
    @GetMapping("/jugadores/buscar/nombre")
    @Operation(summary = "Buscar jugador por nombre", description = "Busca jugadores cuyo nombre contenga el texto proporcionado (búsqueda case-insensitive).")
    @ApiResponse(responseCode = "200", description = "Resultados de búsqueda obtenidos")
    public List<JugadorList> buscarJugadorPorNombre(@RequestParam String nombre) {
        return jugadorService.buscarJugadorPorNombre(nombre);
    }

    @GetMapping("/jugadores/filtrado")
    @Operation(summary = "Buscar jugadores con filtros avanzados", description = "Retorna jugadores con paginación y filtros avanzados. Soporta filtrado por múltiples campos, búsqueda personalizada y ordenamiento.")
    @ApiResponse(responseCode = "200", description = "Resultados filtrados y paginados")
    public ResponseEntity<PaginaResponse<JugadorList>> getAllJugadoresFiltrado(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        List<FiltroBusqueda> listaFiltros = FiltroBusquedaHelper.createFiltroBusqueda(filter);
        Pageable pageable = PaginationHelper.createPageable(page, size, sort);
        PaginaResponse<JugadorList> response = jugadorService.findAllPageJugadorList(listaFiltros, pageable);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/jugadores/{id}")
    @Operation(summary = "Eliminar jugador", description = "Elimina un jugador del sistema de forma permanente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Jugador eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado")
    })
    public ResponseEntity<Void> eliminarJugadorPorId(@PathVariable Long id) {
        jugadorService.eliminarJugadorPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/jugadores/{id}")
    @Operation(summary = "Actualizar jugador", description = "Actualiza los datos de un jugador existente. Todos los campos deben proporcionarse.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Jugador actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Validación fallida - Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado"),
        @ApiResponse(responseCode = "409", description = "Conflicto - Equipo no existe")
    })
    public JugadorEdit actualizarJugador(@PathVariable Long id, @Valid @RequestBody JugadorEdit jugadorEdit) {
        return jugadorService.actualizarJugador(id, jugadorEdit);
    }
}
