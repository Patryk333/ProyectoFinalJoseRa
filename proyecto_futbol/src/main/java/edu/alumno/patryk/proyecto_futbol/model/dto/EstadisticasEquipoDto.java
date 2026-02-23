package edu.alumno.patryk.proyecto_futbol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadisticasEquipoDto {
    private Long idEquipo;
    private String nombreEquipo;
    private Long cantidadJugadores;
    private String ciudad;
}
