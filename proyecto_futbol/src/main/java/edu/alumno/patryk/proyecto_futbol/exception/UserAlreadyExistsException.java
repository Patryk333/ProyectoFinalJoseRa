package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public UserAlreadyExistsException() {
        super("El usuario ya existe");
        this.errorCode = "USER_ALREADY_EXISTS";
    }

    public UserAlreadyExistsException(String message) {
        super(message);
        this.errorCode = "USER_ALREADY_EXISTS";
    }

    public UserAlreadyExistsException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
