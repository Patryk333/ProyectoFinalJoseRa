package edu.alumno.patryk.proyecto_futbol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoResponse;
import edu.alumno.patryk.proyecto_futbol.service.DocEquipoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/futbol/docs")
public class DocEquipoRestController {

    private final DocEquipoService docEquipoService;

    public DocEquipoRestController(DocEquipoService docEquipoService) {
        this.docEquipoService = docEquipoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocEquipoResponse> create(@Valid @ModelAttribute DocEquipoEdit docEquipoEdit) {
        return ResponseEntity.status(HttpStatus.CREATED).body(docEquipoService.create(docEquipoEdit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocEquipoResponse> read(@PathVariable Long id) {
        return ResponseEntity.ok(docEquipoService.read(id));
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<byte[]> previewDocument(@PathVariable Long id) {
        return docEquipoService.getDocumentForPreview(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocEquipoResponse> update(@PathVariable Long id,
                                                     @Valid @ModelAttribute DocEquipoEdit docEquipoEdit) {
        return ResponseEntity.ok(docEquipoService.update(id, docEquipoEdit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        docEquipoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
