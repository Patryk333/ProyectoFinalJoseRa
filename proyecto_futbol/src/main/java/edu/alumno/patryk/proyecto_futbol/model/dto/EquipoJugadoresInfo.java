package edu.alumno.patryk.proyecto_futbol.model.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipoJugadoresInfo {
    private Long id;
    @NotNull(message = "El nombre del equipo no puede estar vacio")
    private String nombre;
    private String estadio;
    private String entrenador;
    private String ciudad;
    @Min(value = 1000, message = "La fecha de fundacion debe ser un año valido de 4 digitos")
    private Integer fundacion;
    @NotNull(message = "El id de la liga no puede estar vacio")
    private Long idLiga;
    private List<JugadorInfo> jugadores;
}
