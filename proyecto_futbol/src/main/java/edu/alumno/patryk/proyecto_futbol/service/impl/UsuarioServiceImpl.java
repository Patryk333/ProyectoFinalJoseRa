package edu.alumno.patryk.proyecto_futbol.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.AuthenticationException;
import edu.alumno.patryk.proyecto_futbol.exception.UserAlreadyExistsException;
import edu.alumno.patryk.proyecto_futbol.model.db.UsuarioDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioCreateRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.UsuarioInfo;
import edu.alumno.patryk.proyecto_futbol.repository.UsuarioRepository;
import edu.alumno.patryk.proyecto_futbol.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioInfo obtenerPorId(Long id) {
        UsuarioDb usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new AuthenticationException("USER_NOT_FOUND", "Usuario no encontrado con id: " + id));
        return convertToDto(usuario);
    }

    @Override
    public UsuarioInfo obtenerPorUsername(String username) {
        UsuarioDb usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("USER_NOT_FOUND", "Usuario no encontrado: " + username));
        return convertToDto(usuario);
    }

    @Override
    public UsuarioInfo crear(UsuarioCreateRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", "El nombre de usuario ya está registrado");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("EMAIL_ALREADY_EXISTS", "El email ya está registrado");
        }

        UsuarioDb nuevoUsuario = new UsuarioDb();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");
        nuevoUsuario.setActivo(request.getActivo() != null ? request.getActivo() : true);

        UsuarioDb usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return convertToDto(usuarioGuardado);
    }

    @Override
    public UsuarioInfo actualizar(Long id, UsuarioCreateRequest request) {
        UsuarioDb usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new AuthenticationException("USER_NOT_FOUND", "Usuario no encontrado con id: " + id));

        if (!usuario.getEmail().equals(request.getEmail()) && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("EMAIL_ALREADY_EXISTS", "El email ya está registrado");
        }

        usuario.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) {
            usuario.setRole(request.getRole());
        }
        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        UsuarioDb usuarioActualizado = usuarioRepository.save(usuario);
        return convertToDto(usuarioActualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new AuthenticationException("USER_NOT_FOUND", "Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioInfo convertToDto(UsuarioDb usuario) {
        return new UsuarioInfo(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getActivo()
        );
    }
}
