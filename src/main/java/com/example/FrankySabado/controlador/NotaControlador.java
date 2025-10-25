package com.example.FrankySabado.controlador;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.*;
import com.example.FrankySabado.servicios.NotaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotaControlador {

    private final NotaServicio notaServicio;
    private static final Set<Roles> VIEW_COMMENT_ROLES = Set.of(Roles.Docente, Roles.Administrador, Roles.Familiar);
    private static final Set<Roles> WRITE_COMMENT_ROLES = Set.of(Roles.Docente);

    public NotaControlador(NotaServicio notaServicio) {
        this.notaServicio = notaServicio;
    }

    // 🟢 HU08 - POST /notas - Registrar una nota individual
    @PostMapping
    public ResponseEntity<NotaDTO> registrarNota(
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader,
            @RequestBody NotaDTO notaDTO) {
        try {
            Roles rol = Roles.valueOf(rolHeader);
            if (notaDTO.getComentario() != null && !WRITE_COMMENT_ROLES.contains(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            NotaDTO notaCreada = notaServicio.registrarNota(notaDTO);
            if (!VIEW_COMMENT_ROLES.contains(rol)) {
                notaCreada.setComentario(null);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(notaCreada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 🟢 HU08 - GET /notas/estudiante/{id} - Listar notas por estudiante
    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<NotaDTO>> obtenerNotasPorEstudiante(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String rolHeader) {
        try {
            Roles rol = null;
            if (rolHeader != null) rol = Roles.valueOf(rolHeader);
//// ACA ESTA EL PROBLEMA
            List<NotaDTO> notas = notaServicio.obtenerNotasPorEstudiante(id);

            if (rol == null || !VIEW_COMMENT_ROLES.contains(rol)) {
                notas.forEach(n -> n.setComentario(null));
            }

            return ResponseEntity.ok(notas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 🟢 HU08 - PUT /notas/{id} - Actualizar una nota
    @PutMapping("/{id}")
    public ResponseEntity<NotaDTO> actualizarNota(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader,
            @RequestBody NotaDTO notaDTO) {
        try {
            Roles rol = Roles.valueOf(rolHeader);
            if (notaDTO.getComentario() != null && !WRITE_COMMENT_ROLES.contains(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            NotaDTO notaActualizada = notaServicio.actualizarNota(id, notaDTO);
            if (!VIEW_COMMENT_ROLES.contains(rol)) {
                notaActualizada.setComentario(null);
            }
            return ResponseEntity.ok(notaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 🟢 HU08 - DELETE /notas/{id} - Eliminar una nota
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarNota(@PathVariable Integer id) {
        try {
            notaServicio.eliminarNota(id);
            return ResponseEntity.ok("Nota eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.noContent().build();
        }
    }

    // GET /notas/{id} - Obtener nota por ID con control de visibilidad
    @GetMapping("/{id}")
    public ResponseEntity<NotaDTO> obtenerNotaPorId(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String rolHeader) {
        try {
            Roles rol = null;
            if (rolHeader != null) {
                rol = Roles.valueOf(rolHeader);
            }

            NotaDTO nota = notaServicio.buscarNotaPorId(id);
            if (rol == null || !VIEW_COMMENT_ROLES.contains(rol)) {
                nota.setComentario(null);
            }
            return ResponseEntity.ok(nota);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // HU20 - GET /notas/docente/{id} - Consultar notas por docente
    @GetMapping("/docente/{id}")
    public ResponseEntity<?> obtenerNotasPorDocente(
            @PathVariable Integer id,
            @RequestParam(value = "materiaId", required = false) Long materiaId,
            @RequestParam(value = "grupoId", required = false) Long grupoId,
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader) {
        try {
            Roles rol = Roles.valueOf(rolHeader);
            if (rol != Roles.Docente && rol != Roles.Administrador) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
            }

            List<NotaDTO> notas = notaServicio.obtenerNotasPorDocente(id, materiaId, grupoId);
            if (!VIEW_COMMENT_ROLES.contains(rol)) {
                notas.forEach(n -> n.setComentario(null));
            }

            return ResponseEntity.ok(notas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol inválido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Endpoints auxiliares
    @GetMapping("/bajo-rendimiento")
    public ResponseEntity<List<RendimientoBajoDTO>> getNotasBajoRendimiento() {
        try {
            return ResponseEntity.ok(notaServicio.obtenerRendimientoBajo());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/registrar-multiples")
    public ResponseEntity<String> registrarMultiplesNotas(@RequestBody RegistrarNotasDTO dto) {
        try {
            notaServicio.registrarMultiplesNotas(dto);
            return ResponseEntity.ok("Notas registradas correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/consolidado/{idEstudiante}")
    public ResponseEntity<List<String>> obtenerConsolidado(@PathVariable Integer idEstudiante) {
        try {
            return ResponseEntity.ok(notaServicio.consolidarNotasPorTipo(idEstudiante));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
