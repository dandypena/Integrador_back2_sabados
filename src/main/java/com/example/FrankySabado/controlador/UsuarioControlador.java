package com.example.FrankySabado.controlador;

import com.example.FrankySabado.dtos.CrearEstudianteDTO;
import com.example.FrankySabado.dtos.CrearDocenteDTO;
import com.example.FrankySabado.dtos.LoginResponseDTO;
import com.example.FrankySabado.dtos.ErrorResponseDTO;
import com.example.FrankySabado.modelos.Docente;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Usuario;
import com.example.FrankySabado.servicios.UsuarioServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // POST /api/usuarios/estudiante - Crear usuario y estudiante
    @PostMapping("/estudiante")
    public ResponseEntity<?> crearEstudiante(@RequestBody CrearEstudianteDTO dto) {
        try {
            Estudiante estudiante = usuarioServicio.crearEstudiante(dto);
            // Crear respuesta con formato esperado por el frontend
            LoginResponseDTO response = new LoginResponseDTO();
            response.setId(estudiante.getUsuario().getId());
            response.setNombre(estudiante.getUsuario().getNombre());
            response.setCorreo(estudiante.getUsuario().getCorreo());
            response.setRol(estudiante.getUsuario().getRol());
            response.setEstudianteId(estudiante.getId());
            response.setSuccess(true);
            response.setMessage("Estudiante registrado exitosamente");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            ErrorResponseDTO error = new ErrorResponseDTO();
            error.setSuccess(false);
            error.setError("REGISTRATION_ERROR");
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // POST /api/usuarios/docente - Crear usuario y docente
    @PostMapping("/docente")
    public ResponseEntity<?> crearDocente(@RequestBody CrearDocenteDTO dto) {
        try {
            Docente docente = usuarioServicio.crearDocente(dto);
            // Crear respuesta con formato esperado por el frontend
            LoginResponseDTO response = new LoginResponseDTO();
            response.setId(docente.getUsuario().getId());
            response.setNombre(docente.getUsuario().getNombre());
            response.setCorreo(docente.getUsuario().getCorreo());
            response.setRol(docente.getUsuario().getRol());
            response.setDocenteId(docente.getId());
            response.setSuccess(true);
            response.setMessage("Docente registrado exitosamente");
            if (docente.getMateriaPrincipal() != null) {
                response.setMateriaId(docente.getMateriaPrincipal().getId());
                response.setMateriaNombre(docente.getMateriaPrincipal().getNombre());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            ErrorResponseDTO error = new ErrorResponseDTO();
            error.setSuccess(false);
            error.setError("REGISTRATION_ERROR");
            error.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (RuntimeException e) {
            ErrorResponseDTO error = new ErrorResponseDTO();
            error.setSuccess(false);
            error.setError("INTERNAL_ERROR");
            error.setMessage("Error al crear docente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // POST /api/usuarios - Crear usuario genérico
    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioServicio.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
