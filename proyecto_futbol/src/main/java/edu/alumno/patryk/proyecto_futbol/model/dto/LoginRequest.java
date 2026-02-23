package edu.alumno.patryk.proyecto_futbol.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Credenciales de usuario para autenticación")
public class LoginRequest {

    @NotNull(message = "El nombre de usuario no puede estar vacio")
    @Schema(description = "Nombre de usuario único registrado en el sistema", example = "juan")
    private String username;

    @NotNull(message = "La contraseña no puede estar vacia")
    @Schema(description = "Contraseña del usuario (case-sensitive)", example = "miPassword123!")
    private String password;
}
