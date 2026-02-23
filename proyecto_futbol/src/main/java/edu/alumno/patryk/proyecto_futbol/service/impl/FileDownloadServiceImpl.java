package edu.alumno.patryk.proyecto_futbol.service.impl;

import java.util.Base64;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.service.FileDownloadService;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    @Override
    public ResponseEntity<byte[]> prepareDownloadResponse(byte[] byteContent, String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        return new ResponseEntity<>(byteContent, headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> prepareDownloadResponse(String base64Content, String contentType, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.inline().filename(fileName).build());
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
        return new ResponseEntity<>(decodedBytes, headers, HttpStatus.OK);
    }
}
