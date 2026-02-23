package edu.alumno.patryk.proyecto_futbol.service;

import edu.alumno.patryk.proyecto_futbol.model.dto.LoginRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.LoginResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse register(RegisterRequest registerRequest);
}
