package edu.alumno.patryk.proyecto_futbol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocEquipoResponse {
    
    private Long id;
    private Long idEquipo;
    private String nombreFichero;
    private String comentario;
    private String base64Documento;
    private String extensionDocumento;
    private String contentTypeDocumento;
    private String creadoPor;
}
