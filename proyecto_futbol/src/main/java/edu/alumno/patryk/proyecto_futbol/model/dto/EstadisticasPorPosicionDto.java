package edu.alumno.patryk.proyecto_futbol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadisticasPorPosicionDto {
    private String posicion;
    private Long cantidad;
}
