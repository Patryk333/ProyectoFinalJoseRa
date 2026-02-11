package edu.alumno.patryk.proyecto_futbol.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JugadorList {
    private Long id;
    @NotNull(message = "El nombre no puede estar vacio")
    private String nombre;
    @Min(value = 1, message = "El dorsal debe ser minimo 1")
    @Max(value = 99, message = "El dorsal solo puede ser un numero de maximo 2 digitos")
    private Integer dorsal;
    @NotNull(message = "La posicion del jugador no puede estar vacia")
    private String posicion;
    @NotNull(message = "La nacionalidad del jugador no puede estar vacia")
    private String nacionalidad;
    @NotNull(message = "La fecha de nacimiento del jugador no puede estar vacia")
    private String fechaNacimiento;
    @NotNull(message = "El id del equipo del jugador no puede estar vacio")
    private Long idEquipo;
}
