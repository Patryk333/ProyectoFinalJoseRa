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
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoInfo;
import edu.alumno.patryk.proyecto_futbol.model.dto.EquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasEquipoDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasGeneralesDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.EstadisticasLigaDto;
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/futbol")
@Tag(name = "Equipos", description = "Operaciones CRUD para gestionar equipos de fútbol. Estos endpoints son públicos y no requieren autenticación.")
public class EquipoRestController {
    
    private final EquipoService equipoService;

    public EquipoRestController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PostMapping("/equipos/crear")
    @Operation(summary = "Crear nuevo equipo", description = "Crea un nuevo equipo de fútbol. El nombre del equipo debe ser único. Se puede especificar el año de fundación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos: nombre requerido, año de fundación debe ser válido"),
        @ApiResponse(responseCode = "409", description = "El nombre del equipo ya existe")
    })
    public EquipoEdit creaEquipoEdit(@Valid @RequestBody EquipoEdit equipoEdit) {
        return equipoService.save(equipoEdit);
    }
    
    @GetMapping("/equipos/{id}")
    @Operation(summary = "Obtener equipo por ID", description = "Recupera la información completa de un equipo específico identificado por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo encontrado"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado con el ID especificado")
    })
    public EquipoInfo obtenerEquipoPorId(@PathVariable Long id) {
        return equipoService.obtenerEquipoPorId(id);
    }
    
    @GetMapping("/equipos")
    @Operation(summary = "Listar todos los equipos", description = "Obtiene la lista completa de todos los equipos registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de equipos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay equipos registrados")
    })
    public List<EquipoList> obtenerTodosEquipos() {
        return equipoService.obtenerTodosEquipos();
    }
    
    @GetMapping("/equipos/buscar/nombre")
    @Operation(summary = "Buscar equipos por nombre", description = "Busca equipos cuyo nombre contenga el texto especificado. La búsqueda es insensible a mayúsculas y minúsculas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda completada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetro 'nombre' requerido"),
        @ApiResponse(responseCode = "204", description = "No se encontraron equipos con ese nombre")
    })
    public List<EquipoList> buscarEquipoPorNombre(@RequestParam String nombre) {
        return equipoService.buscarEquipoPorNombre(nombre);
    }

    @GetMapping("/equipos/filtrado")
    @Operation(summary = "Listar equipos con filtrado y paginación", description = "Obtiene una lista paginada de equipos con opciones avanzadas de filtrado y ordenamiento. Permite filtrar por múltiples criterios y ordenar los resultados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Página de equipos obtenida exitosamente"),
        @ApiResponse(responseCode = "400", description = "Parámetros de paginación u ordenamiento inválidos")
    })
    public ResponseEntity<PaginaResponse<EquipoList>> getAllEquiposFiltrado(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        List<FiltroBusqueda> listaFiltros = FiltroBusquedaHelper.createFiltroBusqueda(filter);
        Pageable pageable = PaginationHelper.createPageable(page, size, sort);
        PaginaResponse<EquipoList> response = equipoService.findAllPageEquipoList(listaFiltros, pageable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/equipos/{id}")
    @Operation(summary = "Eliminar equipo", description = "Elimina un equipo específico del sistema. Esta operación eliminará también todos los jugadores asociados al equipo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Equipo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado con el ID especificado")
    })
    public ResponseEntity<Void> eliminarEquipoPorId(@PathVariable Long id) {
        equipoService.eliminarEquipoPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/equipos/{id}")
    @Operation(summary = "Actualizar equipo", description = "Actualiza la información de un equipo existente. Permite cambiar el nombre, año de fundación y otros datos del equipo.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Equipo actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la actualización"),
        @ApiResponse(responseCode = "404", description = "Equipo no encontrado con el ID especificado"),
        @ApiResponse(responseCode = "409", description = "El nuevo nombre del equipo ya existe en el sistema")
    })
    public EquipoEdit actualizarEquipo(@PathVariable Long id, @Valid @RequestBody EquipoEdit equipoEdit) {
        return equipoService.actualizarEquipo(id, equipoEdit);
    }
    
    @GetMapping("/estadisticas/generales")
    @Operation(summary = "Estadísticas generales del sistema", description = "Devuelve estadísticas globales: total de equipos, jugadores, ligas y promedio de jugadores por equipo.")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    public ResponseEntity<EstadisticasGeneralesDto> obtenerEstadisticasGenerales() {
        return ResponseEntity.ok(equipoService.obtenerEstadisticasGenerales());
    }
    
    @GetMapping("/estadisticas/equipos")
    @Operation(summary = "Estadísticas de jugadores por equipo", description = "Devuelve el número de jugadores que tiene cada equipo junto con su información básica.")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    public ResponseEntity<List<EstadisticasEquipoDto>> obtenerEstadisticasEquipos() {
        return ResponseEntity.ok(equipoService.obtenerEstadisticasEquipos());
    }
    
    @GetMapping("/estadisticas/ligas")
    @Operation(summary = "Estadísticas de equipos por liga", description = "Devuelve el número de equipos y jugadores agrupados por liga.")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas exitosamente")
    public ResponseEntity<List<EstadisticasLigaDto>> obtenerEstadisticasPorLiga() {
        return ResponseEntity.ok(equipoService.obtenerEstadisticasPorLiga());
    }
    
    @GetMapping("/estadisticas/total-equipos")
    @Operation(summary = "Total de equipos", description = "Devuelve el número total de equipos registrados en el sistema.")
    @ApiResponse(responseCode = "200", description = "Total obtenido exitosamente")
    public ResponseEntity<Long> obtenerTotalEquipos() {
        return ResponseEntity.ok(equipoService.obtenerTotalEquipos());
    }
    
}
