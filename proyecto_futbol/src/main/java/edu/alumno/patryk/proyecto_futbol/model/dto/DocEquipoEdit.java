package edu.alumno.patryk.proyecto_futbol.model.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocEquipoEdit {
    
    private Long id;
    
    @NotNull(message = "El ID del equipo no puede ser nulo")
    private Long idEquipo;
    
    @NotNull(message = "El nombre del fichero no puede ser nulo")
    @Size(min = 5, max = 255, message = "El nombre del fichero debe tener entre 5 y 255 caracteres")
    private String nombreFichero;
    
    private String comentario;
    
    private MultipartFile multipart;
    
    @Size(max = 255, message = "El nombre del creador debe tener máximo 255 caracteres")
    private String creadoPor;
}
