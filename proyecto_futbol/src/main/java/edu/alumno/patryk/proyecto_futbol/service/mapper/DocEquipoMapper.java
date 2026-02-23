package edu.alumno.patryk.proyecto_futbol.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import edu.alumno.patryk.proyecto_futbol.model.db.DocEquipoDb;
import edu.alumno.patryk.proyecto_futbol.model.db.DocEquipoEditDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoEdit;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoList;
import edu.alumno.patryk.proyecto_futbol.model.dto.DocEquipoResponse;
import edu.alumno.patryk.proyecto_futbol.utils.MultipartUtils;

@Mapper(componentModel = "spring", imports = MultipartUtils.class)
public interface DocEquipoMapper {
    
    DocEquipoMapper INSTANCE = Mappers.getMapper(DocEquipoMapper.class);
    
    DocEquipoResponse docEquipoEditDbToDocEquipoResponse(DocEquipoEditDb docEquipoEditDb);
    
    @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(docEquipoEdit.getMultipart()))")
    @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(docEquipoEdit.getMultipart()))")
    @Mapping(target = "contentTypeDocumento", expression = "java(docEquipoEdit.getMultipart().getContentType())")
    DocEquipoEditDb docEquipoEditToDocEquipoEditDb(DocEquipoEdit docEquipoEdit);
    
    @Mapping(target = "base64Documento", expression = "java(MultipartUtils.multipartToString(docEquipoEdit.getMultipart()))")
    @Mapping(target = "extensionDocumento", expression = "java(MultipartUtils.getExtensionMultipartfile(docEquipoEdit.getMultipart()))")
    @Mapping(target = "contentTypeDocumento", expression = "java(docEquipoEdit.getMultipart().getContentType())")
    void updateDocEquipoEditDbFromDocEquipoEdit(DocEquipoEdit docEquipoEdit, @MappingTarget DocEquipoEditDb docEquipoEditDb);
    
    DocEquipoList docEquipoDbToDocEquipoList(DocEquipoDb docEquipoDb);
    
    List<DocEquipoList> docsEquipoDbToDocsEquipoList(List<DocEquipoDb> docEquiposDb);
}
