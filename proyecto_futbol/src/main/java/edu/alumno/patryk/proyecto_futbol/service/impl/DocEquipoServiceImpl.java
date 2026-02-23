package edu.alumno.patryk.proyecto_futbol.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.DocEquipoNotFoundException;
import edu.alumno.patryk.proyecto_futbol.exception.InvalidEntityException;
import edu.alumno.patryk.proyecto_futbol.exception.MultipartProcessingException;
import edu.alumno.patryk.proyecto_futbol.model.db.DocEquipoEditDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoResponse;
import edu.alumno.patryk.proyecto_futbol.repository.DocEquipoCrudRepository;
import edu.alumno.patryk.proyecto_futbol.service.DocEquipoService;
import edu.alumno.patryk.proyecto_futbol.service.FileDownloadService;
import edu.alumno.patryk.proyecto_futbol.service.mapper.DocEquipoMapper;

@Service
public class DocEquipoServiceImpl implements DocEquipoService {

    private final DocEquipoCrudRepository docEquipoCrudRepository;
    private final FileDownloadService fileDownloadService;

    public DocEquipoServiceImpl(DocEquipoCrudRepository docEquipoCrudRepository,
                                FileDownloadService fileDownloadService) {
        this.docEquipoCrudRepository = docEquipoCrudRepository;
        this.fileDownloadService = fileDownloadService;
    }

    @Override
    public DocEquipoResponse create(DocEquipoEdit docEquipoEdit) {
        if (docEquipoEdit.getMultipart() == null || docEquipoEdit.getMultipart().isEmpty()) {
            throw new MultipartProcessingException("BAD_MULTIPART", "El archivo no puede estar vacío");
        }
        
        if (docEquipoEdit.getId() != null) {
            throw new InvalidEntityException("DOC_EQUIPO_ID_MISMATCH",
                "El ID debe ser nulo al crear un nuevo documento.");
        }

        DocEquipoEditDb entity = DocEquipoMapper.INSTANCE.docEquipoEditToDocEquipoEditDb(docEquipoEdit);
        DocEquipoEditDb savedEntity = docEquipoCrudRepository.save(entity);
        return DocEquipoMapper.INSTANCE.docEquipoEditDbToDocEquipoResponse(savedEntity);
    }

    @Override
    public DocEquipoResponse read(Long id) {
        DocEquipoEditDb entity = docEquipoCrudRepository.findById(id)
                .orElseThrow(() -> new DocEquipoNotFoundException("DOC_EQUIPO_NOT_FOUND", 
                    "No se encontró el documento con ID " + id));
        return DocEquipoMapper.INSTANCE.docEquipoEditDbToDocEquipoResponse(entity);
    }

    @Override
    public ResponseEntity<byte[]> getDocumentForPreview(Long id) {
        DocEquipoEditDb doc = docEquipoCrudRepository.findById(id)
                .orElseThrow(() -> new DocEquipoNotFoundException("DOC_EQUIPO_NOT_FOUND", 
                    "No se encontró el documento con ID " + id));
        
        return fileDownloadService.prepareDownloadResponse(
            doc.getBase64Documento(), 
            doc.getContentTypeDocumento(), 
            doc.getNombreFichero()
        );
    }

    @Override
    public DocEquipoResponse update(Long id, DocEquipoEdit docEquipoEdit) {
        DocEquipoEditDb existingEntity = docEquipoCrudRepository.findById(id)
                .orElseThrow(() -> new DocEquipoNotFoundException("DOC_EQUIPO_NOT_FOUND", 
                    "No se puede actualizar. El documento con ID " + id + " no existe."));
        
        DocEquipoMapper.INSTANCE.updateDocEquipoEditDbFromDocEquipoEdit(docEquipoEdit, existingEntity);
        return DocEquipoMapper.INSTANCE.docEquipoEditDbToDocEquipoResponse(
            docEquipoCrudRepository.save(existingEntity));
    }

    @Override
    public void delete(Long id) {
        if (docEquipoCrudRepository.existsById(id)) {
            docEquipoCrudRepository.deleteById(id);
        }
    }
}
