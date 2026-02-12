package edu.alumno.patryk.proyecto_futbol.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipos")
public class EquipoDb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "El nombre del equipo no puede estar vacio")
    private String nombre;
    private String estadio;
    private String entrenador;
    private String ciudad;
    private Integer fundacion;
    @NotNull(message = "El id de la liga no puede estar vacio")
    private Long idLiga;
}
