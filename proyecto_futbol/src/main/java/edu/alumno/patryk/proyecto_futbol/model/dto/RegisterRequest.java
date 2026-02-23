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
@Schema(description = "Datos para registrar un nuevo usuario")
public class RegisterRequest {

    @NotNull(message = "El nombre de usuario no puede estar vacio")
    @Schema(description = "Nombre de usuario único (3-50 caracteres)", example = "juan", minLength = 3, maxLength = 50)
    private String username;

    @NotNull(message = "El email no puede estar vacio")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email válido y único del usuario", example = "juan@example.com")
    private String email;

    @NotNull(message = "La contraseña no puede estar vacia")
    @Schema(description = "Contraseña segura (mínimo 8 caracteres recomendado)", example = "miPassword123!", minLength = 8)
    private String password;

    @NotNull(message = "El rol no puede estar vacio")
    @Schema(description = "Rol inicial del usuario", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN"})
    private String role;
}
