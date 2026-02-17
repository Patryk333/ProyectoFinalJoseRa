package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public AuthenticationException() {
        super("Error de autenticación");
        this.errorCode = "AUTH_ERROR";
    }

    public AuthenticationException(String message) {
        super(message);
        this.errorCode = "AUTH_ERROR";
    }

    public AuthenticationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "AUTH_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
