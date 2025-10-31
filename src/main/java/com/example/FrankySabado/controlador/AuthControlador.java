package com.example.FrankySabado.controlador;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.ErrorResponseDTO;
import com.example.FrankySabado.dtos.LoginDTO;
import com.example.FrankySabado.dtos.LoginResponseDTO;
import com.example.FrankySabado.modelos.Docente;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Usuario;
import com.example.FrankySabado.repositorios.DocenteRepository;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación
 * Maneja login y registro de usuarios
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthControlador {

    private final UsuarioServicio usuarioServicio;
    private final DocenteRepository docenteRepository;
    private final EstudianteRepository estudianteRepository;

    public AuthControlador(UsuarioServicio usuarioServicio, DocenteRepository docenteRepository, EstudianteRepository estudianteRepository) {
        this.usuarioServicio = usuarioServicio;
        this.docenteRepository = docenteRepository;
        this.estudianteRepository = estudianteRepository;
    }

    /**
     * Login de usuario
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            // Autenticar usuario
            Usuario usuario = usuarioServicio.autenticar(loginDTO);
            
            // Crear respuesta exitosa
            LoginResponseDTO response = new LoginResponseDTO();
            response.setId(usuario.getId());
            response.setNombre(usuario.getNombre());
            response.setCorreo(usuario.getCorreo());
            response.setRol(usuario.getRol());
            response.setMessage("Login exitoso");
            response.setSuccess(true);

            // Si es docente, incluir información de su materia principal y su ID de docente
            if (usuario.getRol() == Roles.Docente) {
                Docente docente = docenteRepository.findByUsuario(usuario)
                        .orElse(null);
                if (docente != null) {
                    response.setDocenteId(docente.getId());
                    if (docente.getMateriaPrincipal() != null) {
                        response.setMateriaId(docente.getMateriaPrincipal().getId());
                        response.setMateriaNombre(docente.getMateriaPrincipal().getNombre());
                    }
                }
            }

            // Si es estudiante, incluir su ID de estudiante
            if (usuario.getRol() == Roles.Estudiante) {
                Estudiante estudiante = estudianteRepository.findByUsuario(usuario)
                        .orElse(null);
                if (estudiante != null) {
                    response.setEstudianteId(estudiante.getId());
                }
            }
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            // Manejar error de autenticación
            ErrorResponseDTO error = new ErrorResponseDTO();
            error.setSuccess(false);
            error.setError("AUTHENTICATION_ERROR");
            error.setMessage(e.getMessage());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    /**
     * Verificar si un correo ya está registrado
     * GET /api/auth/verificar-correo?correo=ejemplo@correo.com
     */
    @GetMapping("/verificar-correo")
    public ResponseEntity<?> verificarCorreo(@RequestParam String correo) {
        try {
            usuarioServicio.buscarPorCorreo(correo);
            // Si encuentra el usuario, el correo ya está registrado
            return ResponseEntity.ok(new ErrorResponseDTO("El correo ya está registrado"));
        } catch (RuntimeException e) {
            // Si no encuentra el usuario, el correo está disponible
            return ResponseEntity.ok(new LoginResponseDTO(null, null, correo, null, "Correo disponible", true));
        }
    }

    /**
     * Obtener información del usuario autenticado
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> obtenerUsuarioActual(@RequestParam Integer id) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
            
            LoginResponseDTO response = new LoginResponseDTO();
            response.setId(usuario.getId());
            response.setNombre(usuario.getNombre());
            response.setCorreo(usuario.getCorreo());
            response.setRol(usuario.getRol());
            response.setSuccess(true);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponseDTO error = new ErrorResponseDTO("Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
