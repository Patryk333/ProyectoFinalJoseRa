package edu.alumno.patryk.proyecto_futbol.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Respuesta de autenticación con token JWT")
public class LoginResponse {

    @Schema(description = "Token JWT para autenticar futuras solicitudes", example = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoianVhbiIsImlhdCI6MTcwODY5NjAwMCwiZXhwIjoxNzA4NzA2ODAwfQ....")
    private String token;

    @Schema(description = "Tipo de token (siempre Bearer para JWT)", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre de usuario único", example = "juan")
    private String username;

    @Schema(description = "Email del usuario", example = "juan@example.com")
    private String email;

    @Schema(description = "Rol del usuario en el sistema", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN"})
    private String role;

    public LoginResponse(String token, Long id, String username, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
