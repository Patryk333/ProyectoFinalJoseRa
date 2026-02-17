package edu.alumno.patryk.proyecto_futbol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioCreateRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioInfo;
import edu.alumno.patryk.proyecto_futbol.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/secure/usuarios")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Usuarios Securizados", description = "Operaciones de gestión de usuarios que requieren autenticación JWT. Todos los endpoints de esta sección necesitan un token válido.")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    @Operation(summary = "Obtener perfil del usuario autenticado", 
        description = "Retorna los datos del usuario actualmente autenticado. El usuario se obtiene del token JWT proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioInfo.class),
                examples = @ExampleObject(
                    name = "Perfil de usuario",
                    value = "{\n" +
                            "  \"id\": 1,\n" +
                            "  \"username\": \"juan\",\n" +
                            "  \"email\": \"juan@example.com\",\n" +
                            "  \"role\": \"ROLE_USER\",\n" +
                            "  \"activo\": true\n" +
                            "}"
                ))),
        @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o faltante")
    })
    public ResponseEntity<UsuarioInfo> obtenerPerfil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UsuarioInfo usuarioInfo = usuarioService.obtenerPorUsername(username);
        return ResponseEntity.ok(usuarioInfo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", 
        description = "Retorna los datos de un usuario específico basado en su ID. Requiere autenticación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioInfo.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID especificado")
    })
    public ResponseEntity<UsuarioInfo> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioInfo usuarioInfo = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(usuarioInfo);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo usuario", 
        description = "Crea un nuevo usuario en el sistema. La contraseña se encripta automáticamente. Requiere autenticación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioInfo.class),
                examples = @ExampleObject(
                    name = "Usuario creado",
                    value = "{\n" +
                            "  \"id\": 3,\n" +
                            "  \"username\": \"carlos\",\n" +
                            "  \"email\": \"carlos@example.com\",\n" +
                            "  \"role\": \"ROLE_USER\",\n" +
                            "  \"activo\": true\n" +
                            "}"
                ))),
        @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "409", description = "Conflicto - Username o email ya existe"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos faltantes o formato incorrecto")
    })
    public ResponseEntity<UsuarioInfo> crearUsuario(@Valid @RequestBody UsuarioCreateRequest request) {
        UsuarioInfo usuarioInfo = usuarioService.crear(request);
        return ResponseEntity.status(201).body(usuarioInfo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", 
        description = "Actualiza los datos de un usuario existente. Los campos vacíos se ignoran. Si proporciona contraseña, será encriptada. Requiere autenticación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = UsuarioInfo.class))),
        @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID especificado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida - Campos con formato incorrecto")
    })
    public ResponseEntity<UsuarioInfo> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioCreateRequest request) {
        UsuarioInfo usuarioInfo = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(usuarioInfo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", 
        description = "Elimina un usuario del sistema de forma permanente. Requiere autenticación.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autenticado - Token JWT inválido o faltante"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado con el ID especificado")
    })
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
