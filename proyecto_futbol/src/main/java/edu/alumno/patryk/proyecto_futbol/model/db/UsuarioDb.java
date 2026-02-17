package edu.alumno.patryk.proyecto_futbol.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "usuarios")
public class UsuarioDb {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "El nombre de usuario no puede estar vacio")
    private String username;
    
    @NotNull(message = "El email no puede estar vacio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @NotNull(message = "La contraseña no puede estar vacia")
    private String password;
    
    @NotNull(message = "El rol no puede estar vacio")
    private String role;
    
    @NotNull(message = "El estado del usuario no puede estar vacio")
    private Boolean activo = true;
}
