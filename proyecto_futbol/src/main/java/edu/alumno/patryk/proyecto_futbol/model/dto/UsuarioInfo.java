package edu.alumno.patryk.proyecto_futbol.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Información pública de un usuario del sistema")
public class UsuarioInfo {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre de usuario único", example = "juan")
    private String username;

    @Schema(description = "Email del usuario", example = "juan@example.com")
    private String email;

    @Schema(description = "Rol del usuario en el sistema", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN"})
    private String role;

    @Schema(description = "Estado de actividad del usuario", example = "true")
    private Boolean activo;
}
