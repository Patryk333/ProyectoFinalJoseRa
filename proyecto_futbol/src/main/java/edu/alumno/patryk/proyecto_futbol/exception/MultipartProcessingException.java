package edu.alumno.patryk.proyecto_futbol.exception;

public class MultipartProcessingException extends RuntimeException {
    
    private final String errorCode;
    
    public MultipartProcessingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
