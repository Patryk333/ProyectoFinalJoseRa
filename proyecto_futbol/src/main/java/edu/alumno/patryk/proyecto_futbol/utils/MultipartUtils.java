package edu.alumno.patryk.proyecto_futbol.utils;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import edu.alumno.patryk.proyecto_futbol.exception.MultipartProcessingException;

public class MultipartUtils {

    private MultipartUtils() {
        throw new UnsupportedOperationException("Esta clase no debe ser instanciada");
    }

    public static byte[] multipartToBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new MultipartProcessingException("BAD_MULTIPART", 
                "Error al procesar el archivo multipart: " + e.getMessage());
        }
    }

    public static String getExtensionMultipartfile(MultipartFile fichero) {
        if (fichero == null || fichero.isEmpty()) {
            return "";
        }
        
        String nombreFichero = fichero.getOriginalFilename();
        if (nombreFichero == null || !nombreFichero.contains(".")) {
            return "";
        }
        
        return nombreFichero.substring(nombreFichero.lastIndexOf(".") + 1);
    }

    public static String multipartToString(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return "";
        }
        return Base64.getEncoder().encodeToString(multipartToBytes(file));
    }
}
