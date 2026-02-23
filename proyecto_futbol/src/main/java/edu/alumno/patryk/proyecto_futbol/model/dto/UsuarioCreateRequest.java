package edu.alumno.patryk.proyecto_futbol.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Datos para crear o actualizar un usuario")
public class UsuarioCreateRequest {

    @NotNull(message = "El nombre de usuario no puede estar vacio")
    @Schema(description = "Nombre de usuario único (3-50 caracteres)", example = "newuser", minLength = 3, maxLength = 50)
    private String username;

    @NotNull(message = "El email no puede estar vacio")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email válido del usuario", example = "newuser@example.com")
    private String email;

    @NotNull(message = "La contraseña no puede estar vacia")
    @Schema(description = "Contraseña del usuario. Si se omite en actualización, no se cambia. (mínimo 8 caracteres)", example = "securePassword123!", minLength = 8)
    private String password;

    @Schema(description = "Rol del usuario (opcional en actualización)", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN"})
    private String role;

    @Schema(description = "Estado de actividad del usuario (opcional)", example = "true")
    private Boolean activo;
}
