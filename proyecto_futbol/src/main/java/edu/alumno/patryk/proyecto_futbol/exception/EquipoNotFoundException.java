package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EquipoNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public EquipoNotFoundException() {
        super("Equipo no encontrado");
        this.errorCode = "Equipo_NOT_FOUND";
    }

    public EquipoNotFoundException(String message) {
        super(message);
        this.errorCode = "Equipo_NOT_FOUND";
    }

    public EquipoNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public EquipoNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "Equipo_NOT_FOUND";
    }

    public EquipoNotFoundException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
