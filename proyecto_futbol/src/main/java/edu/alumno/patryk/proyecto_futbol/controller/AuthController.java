package edu.alumno.patryk.proyecto_futbol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.LoginRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.LoginResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.RegisterRequest;
import edu.alumno.patryk.proyecto_futbol.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios. Genera tokens JWT para acceder a recursos securizados.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuario", description = "Valida las credenciales del usuario y devuelve un token JWT válido para 3 horas. Use este token en el header 'Authorization: Bearer <token>' para acceder a endpoints securizados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa - Token JWT generado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class),
                examples = @ExampleObject(
                    name = "Respuesta exitosa",
                    value = "{\n" +
                            "  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoianVhbiIsImlhdCI6MTcwODY5NjAwMCwiZXhwIjoxNzA4NzA2ODAwfQ.ejemplo\",\n" +
                            "  \"type\": \"Bearer\",\n" +
                            "  \"id\": 1,\n" +
                            "  \"username\": \"juan\",\n" +
                            "  \"email\": \"juan@example.com\",\n" +
                            "  \"role\": \"ROLE_USER\"\n" +
                            "}"
                ))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas - Usuario no existe o contraseña incorrecta"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos faltantes o formato incorrecto")
    })
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody(required = true) LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "Crea una nueva cuenta de usuario en el sistema y devuelve inmediatamente un token JWT válido. La contraseña se encripta usando BCrypt.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente - Token JWT generado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginResponse.class),
                examples = @ExampleObject(
                    name = "Usuario creado",
                    value = "{\n" +
                            "  \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwic3ViIjoibmV3dXNlciIsImlhdCI6MTcwODY5NjAwMCwiZXhwIjoxNzA4NzA2ODAwfQ.ejemplo\",\n" +
                            "  \"type\": \"Bearer\",\n" +
                            "  \"id\": 2,\n" +
                            "  \"username\": \"newuser\",\n" +
                            "  \"email\": \"newuser@example.com\",\n" +
                            "  \"role\": \"ROLE_USER\"\n" +
                            "}"
                ))),
        @ApiResponse(responseCode = "409", description = "Conflicto - Username o email ya registrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos faltantes o formato incorrecto")
    })
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody(required = true) RegisterRequest registerRequest) {
        LoginResponse loginResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
    }
}
