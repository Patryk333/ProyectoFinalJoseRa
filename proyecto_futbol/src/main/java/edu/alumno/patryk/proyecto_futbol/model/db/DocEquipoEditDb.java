package edu.alumno.patryk.proyecto_futbol.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "docequipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocEquipoEditDb {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "idequipo", nullable = false)
    private Long idEquipo;
    
    @Column(name = "nombrefichero", nullable = false, length = 255)
    private String nombreFichero;
    
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
    
    @Column(name = "base64documento", columnDefinition = "TEXT")
    private String base64Documento;
    
    @Column(name = "extensiondocumento", length = 5)
    private String extensionDocumento;
    
    @Column(name = "contenttypedocumento", length = 50)
    private String contentTypeDocumento;
    
    @Column(name = "creadopor", length = 255)
    private String creadoPor;
}
