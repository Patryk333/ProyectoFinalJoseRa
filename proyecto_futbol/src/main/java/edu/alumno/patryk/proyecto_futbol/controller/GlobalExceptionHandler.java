package edu.alumno.patryk.proyecto_futbol.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.alumno.patryk.proyecto_futbol.exception.CustomErrorResponse;
import edu.alumno.patryk.proyecto_futbol.exception.EquipoNotFoundException;
import edu.alumno.patryk.proyecto_futbol.exception.FiltroException;
import edu.alumno.patryk.proyecto_futbol.exception.JugadorNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
    }

    @ExceptionHandler(JugadorNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleJugadorNotFoundException(JugadorNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(EquipoNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleEquipoNotFoundException(EquipoNotFoundException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FiltroException.class)
    public ResponseEntity<CustomErrorResponse> handleFiltroException(FiltroException ex) {
        CustomErrorResponse response = new CustomErrorResponse(ex.getErrorCode(), ex.getDetailedMessage(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGeneralException(Exception ex) {
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : "No cause available";
        String stackTraceElement = ex.getStackTrace().length > 0 
            ? ex.getStackTrace()[0].toString() 
            : "No stack trace available";
    
        CustomErrorResponse errorResponse = new CustomErrorResponse(
            ex.getClass().getSimpleName().toUpperCase(),
            cause,
            "Error en: " + stackTraceElement + " | Mensaje: " + ex.getMessage()
        );
    
        ex.printStackTrace();
    
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}