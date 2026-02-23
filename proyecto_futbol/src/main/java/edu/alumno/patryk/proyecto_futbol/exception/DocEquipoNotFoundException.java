package edu.alumno.patryk.proyecto_futbol.exception;

public class DocEquipoNotFoundException extends RuntimeException {
    
    private final String errorCode;
    
    public DocEquipoNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
