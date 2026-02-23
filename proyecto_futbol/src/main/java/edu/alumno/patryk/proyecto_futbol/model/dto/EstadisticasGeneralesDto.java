package edu.alumno.patryk.proyecto_futbol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EstadisticasGeneralesDto {
    private Long totalEquipos;
    private Long totalJugadores;
    private Long totalLigas;
    private Double promediaJugadoresPorEquipo;
}
