package edu.alumno.patryk.proyecto_futbol.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.alumno.patryk.proyecto_futbol.exception.AuthenticationException;
import edu.alumno.patryk.proyecto_futbol.exception.UserAlreadyExistsException;
import edu.alumno.patryk.proyecto_futbol.model.db.UsuarioDb;
import edu.alumno.patryk.proyecto_futbol.model.dto.LoginRequest;
import edu.alumno.patryk.proyecto_futbol.model.dto.LoginResponse;
import edu.alumno.patryk.proyecto_futbol.model.dto.RegisterRequest;
import edu.alumno.patryk.proyecto_futbol.repository.UsuarioRepository;
import edu.alumno.patryk.proyecto_futbol.security.JwtUtil;
import edu.alumno.patryk.proyecto_futbol.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsuarioDb usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AuthenticationException("AUTH_INVALID_CREDENTIALS", "Usuario o contraseña inválidos"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new AuthenticationException("AUTH_INVALID_CREDENTIALS", "Usuario o contraseña inválidos");
        }

        if (!usuario.getActivo()) {
            throw new AuthenticationException("AUTH_INACTIVE_USER", "El usuario no está activo");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRole());

        return new LoginResponse(token, usuario.getId(), usuario.getUsername(), usuario.getEmail(), usuario.getRole());
    }

    @Override
    public LoginResponse register(RegisterRequest registerRequest) {
        if (usuarioRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("USER_ALREADY_EXISTS", "El nombre de usuario ya está registrado");
        }

        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("EMAIL_ALREADY_EXISTS", "El email ya está registrado");
        }

        UsuarioDb nuevoUsuario = new UsuarioDb();
        nuevoUsuario.setUsername(registerRequest.getUsername());
        nuevoUsuario.setEmail(registerRequest.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        nuevoUsuario.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "ROLE_USER");
        nuevoUsuario.setActivo(true);

        UsuarioDb usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        String token = jwtUtil.generateToken(usuarioGuardado.getUsername(), usuarioGuardado.getRole());

        return new LoginResponse(token, usuarioGuardado.getId(), usuarioGuardado.getUsername(), usuarioGuardado.getEmail(), usuarioGuardado.getRole());
    }
}
