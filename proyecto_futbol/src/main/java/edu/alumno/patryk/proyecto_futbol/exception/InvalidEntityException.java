package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEntityException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String errorCode;
    
    public InvalidEntityException() {
        super("Entidad inválida");
        this.errorCode = "INVALID_ENTITY";
    }
    
    public InvalidEntityException(String message) {
        super(message);
        this.errorCode = "INVALID_ENTITY";
    }
    
    public InvalidEntityException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "INVALID_ENTITY";
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
