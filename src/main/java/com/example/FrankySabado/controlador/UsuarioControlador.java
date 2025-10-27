package com.example.FrankySabado.controlador;

import com.example.FrankySabado.dtos.CrearEstudianteDTO;
import com.example.FrankySabado.dtos.CrearDocenteDTO;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // POST /api/usuarios/docente - Crear usuario y docente
    @PostMapping("/docente")
    public ResponseEntity<?> crearDocente(@RequestBody CrearDocenteDTO dto) {
        try {
            Docente docente = usuarioServicio.crearDocente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(docente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear docente: " + e.getMessage());
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
