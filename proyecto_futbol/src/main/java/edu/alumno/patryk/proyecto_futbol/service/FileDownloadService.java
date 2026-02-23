package edu.alumno.patryk.proyecto_futbol.service;

import org.springframework.http.ResponseEntity;

public interface FileDownloadService {
    
    ResponseEntity<byte[]> prepareDownloadResponse(byte[] byteContent, String contentType, String fileName);
    
    ResponseEntity<byte[]> prepareDownloadResponse(String base64Content, String contentType, String fileName);
}
