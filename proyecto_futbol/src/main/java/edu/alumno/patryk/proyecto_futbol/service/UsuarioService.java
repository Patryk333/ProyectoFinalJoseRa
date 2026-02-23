package edu.alumno.patryk.proyecto_futbol.service;

import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioCreateRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioInfo;

public interface UsuarioService {
    UsuarioInfo obtenerPorId(Long id);
    UsuarioInfo obtenerPorUsername(String username);
    UsuarioInfo crear(UsuarioCreateRequest request);
    UsuarioInfo actualizar(Long id, UsuarioCreateRequest request);
    void eliminar(Long id);
}
