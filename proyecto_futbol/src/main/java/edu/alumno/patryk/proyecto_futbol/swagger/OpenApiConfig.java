package edu.alumno.patryk.proyecto_futbol.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Patryk",
                        email = "patryk.developer@gmail.com",
                        url = "https://github.com/Patryk333"
                ),
                description = "API RESTful completa para la gestión de equipos y jugadores de fútbol con autenticación JWT.",
                title = "API REST Gestión de Fútbol",
                version = "1.0.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "https://github.com/Patryk333/ProyectoFinalJoseRa"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8090"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://api.example.com"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication - Ingresa tu token JWT para autenticación",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}

