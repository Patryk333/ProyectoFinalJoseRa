package edu.alumno.patryk.proyecto_futbol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class JugadorNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    private String errorCode;

    public JugadorNotFoundException() {
        super("Jugador no encontrado");
        this.errorCode = "Jugador_NOT_FOUND";
    }

    public JugadorNotFoundException(String message) {
        super(message);
        this.errorCode = "Jugador_NOT_FOUND";
    }

    public JugadorNotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public JugadorNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "Jugador_NOT_FOUND";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
