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
import edu.alumno.patryk.proyecto_futbol.model.dto.FiltroBusqueda;
import edu.alumno.patryk.proyecto_futbol.model.dto.PaginaResponse;
import edu.alumno.patryk.proyecto_futbol.service.EquipoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/futbol")
public class EquipoRestController {
    
    private final EquipoService equipoService;

    public EquipoRestController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PostMapping("/equipos/crear")
    public EquipoEdit creaEquipoEdit(@Valid @RequestBody EquipoEdit equipoEdit) {
        return equipoService.save(equipoEdit);
    }
    
    @GetMapping("/equipos/{id}")
    public EquipoInfo obtenerEquipoPorId(@PathVariable Long id) {
        return equipoService.obtenerEquipoPorId(id);
    }
    
    @GetMapping("/equipos")
    public List<EquipoList> obtenerTodosEquipos() {
        return equipoService.obtenerTodosEquipos();
    }
    
    @GetMapping("/equipos/buscar/nombre")
    public List<EquipoList> buscarEquipoPorNombre(@RequestParam String nombre) {
        return equipoService.buscarEquipoPorNombre(nombre);
    }

    @GetMapping("/equipos/filtrado")
    public ResponseEntity<PaginaResponse<EquipoList>> getAllEquiposFiltrado(
            @RequestParam(required = false) String[] filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        //Configurar filtros
        List<FiltroBusqueda> listaFiltros = FiltroBusquedaHelper.createFiltroBusqueda(filter);
        //Configurar ordenamiento
        Pageable pageable = PaginationHelper.createPageable(page, size, sort);
        //Recuperar los datos del service
        PaginaResponse<EquipoList> response = equipoService.findAllPageEquipoList(listaFiltros, pageable);
        //Devolver respuesta
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> eliminarEquipoPorId(@PathVariable Long id) {
        equipoService.eliminarEquipoPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/equipos/{id}")
    public EquipoEdit actualizarEquipo(@PathVariable Long id, @Valid @RequestBody EquipoEdit equipoEdit) {
        return equipoService.actualizarEquipo(id, equipoEdit);
    }
    
}
