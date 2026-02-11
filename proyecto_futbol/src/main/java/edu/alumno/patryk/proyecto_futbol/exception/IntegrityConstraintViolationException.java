package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IntegrityConstraintViolationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String errorCode;
    
    public IntegrityConstraintViolationException() {
        super("Violación de restricción de integridad");
        this.errorCode = "INTEGRITY_CONSTRAINT_VIOLATION";
    }
    
    public IntegrityConstraintViolationException(String message) {
        super(message);
        this.errorCode = "INTEGRITY_CONSTRAINT_VIOLATION";
    }
    
    public IntegrityConstraintViolationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public IntegrityConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "INTEGRITY_CONSTRAINT_VIOLATION";
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
