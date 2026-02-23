package edu.alumno.patryk.proyecto_futbol.service;

import org.springframework.http.ResponseEntity;

import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoResponse;

public interface DocEquipoService {
    
    DocEquipoResponse create(DocEquipoEdit docEquipoEdit);
    
    DocEquipoResponse read(Long id);
    
    DocEquipoResponse update(Long id, DocEquipoEdit docEquipoEdit);
    
    void delete(Long id);
    
    ResponseEntity<byte[]> getDocumentForPreview(Long id);
}
