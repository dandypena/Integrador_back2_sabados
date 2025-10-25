package com.example.FrankySabado.controlador;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.*;
import com.example.FrankySabado.modelos.Nota;
import com.example.FrankySabado.repositorios.NotaRepositorio;
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

    public NotaControlador(NotaServicio notaServicio) {
        this.notaServicio = notaServicio;
    }

    private static final Set<Roles> VIEW_COMMENT_ROLES = Set.of(Roles.Docente, Roles.Administrador, Roles.Familiar);
    private static final Set<Roles> WRITE_COMMENT_ROLES = Set.of(Roles.Docente);

    // 🟢 HU08 - POST /notas - Registrar una nota individual
    @PostMapping
    public ResponseEntity<NotaDTO> registrarNota(
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader,
            @RequestBody NotaDTO notaDTO) {
        try {
            Roles rol = Roles.valueOf(rolHeader);
            // Si viene comentario, solo Docente puede añadirlo
            if (notaDTO.getComentario() != null && !WRITE_COMMENT_ROLES.contains(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            NotaDTO notaCreada = notaServicio.registrarNota(notaDTO);
            // Si el rol no puede ver comentarios, limpiar antes de responder
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

            List<NotaDTO> notas = notaServicio.obtenerNotasPorEstudiante(id);

            // Si el rol no puede ver comentarios, limpiar campo comentario
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
            // Si intenta escribir comentario, validar permisos
            if (notaDTO.getComentario() != null && !WRITE_COMMENT_ROLES.contains(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            NotaDTO notaActualizada = notaServicio.actualizarNota(id, notaDTO);
            // Limpiar comentario si rol no tiene permiso de visualización
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

    @GetMapping("/bajo-rendimiento")
    public List<RendimientoBajoDTO> getNotasBajoRendimiento() {
        return notaServicio.obtenerRendimientoBajo();
    }

    @PostMapping("/registrar-multiples")
    public String registrarMultiplesNotas(@RequestBody RegistrarNotasDTO dto) {
        notaServicio.registrarMultiplesNotas(dto);
        return "Notas registradas correctamente.";
    }

    @GetMapping("/consolidado/{idEstudiante}")
    public List<String> obtenerConsolidado(@PathVariable Integer idEstudiante) {
        return notaServicio.consolidarNotasPorTipo(idEstudiante);
    }

    // 🟢 HU10 - GET /notas/materia/{materiaId}/grupo/{grupoId} - Obtener notas por materia y grupo
    @GetMapping("/materia/{materiaId}/grupo/{grupoId}")
    public ResponseEntity<?> obtenerNotasPorMateriaYGrupo(
            @PathVariable Long materiaId,
            @PathVariable Long grupoId,
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader) {
        try {
            // Convertir el rol del header a enum
            Roles rol = Roles.valueOf(rolHeader);

            List<EstudianteNotasDTO> resultado = notaServicio.obtenerNotasPorMateriaYGrupo(materiaId, grupoId, rol);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Rol inválido proporcionado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    // 🟢 HU15 - GET /notas/familiar/estudiante/{estudianteId} - Visualizar notas desde módulo familiar
    @GetMapping("/familiar/estudiante/{estudianteId}")
    public ResponseEntity<?> obtenerNotasParaFamiliar(
            @PathVariable Integer estudianteId,
            @RequestHeader(value = "X-User-Id", required = true) Integer familiarUsuarioId,
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader) {
        try {
            // Convertir el rol del header a enum
            Roles rol = Roles.valueOf(rolHeader);

            List<NotaDTO> notas = notaServicio.obtenerNotasParaFamiliar(estudianteId, familiarUsuarioId, rol);
            // Familiar está en la lista de VIEW_COMMENT_ROLES, así que puede ver comentario
            return ResponseEntity.ok(notas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Rol inválido proporcionado");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    // 🟢 HU18 - GET /notas/evolucion/estudiante/{id} - Consultar evolución académica por periodos
    @GetMapping("/evolucion/estudiante/{id}")
    public ResponseEntity<List<EvolucionAcademicaDTO>> obtenerEvolucionAcademica(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String rolHeader) {
        try {
            Roles rol = null;
            if (rolHeader != null) rol = Roles.valueOf(rolHeader);

            List<EvolucionAcademicaDTO> evolucion = notaServicio.obtenerEvolucionAcademica(id);

            // Si rol no puede ver comentarios, limpiar comentarios dentro de cada nota
            if (rol == null || !VIEW_COMMENT_ROLES.contains(rol)) {
                evolucion.forEach(e -> {
                    if (e.getNotas() != null) {
                        e.getNotas().forEach(n -> n.setComentario(null));
                    }
                });
            }

            return ResponseEntity.ok(evolucion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Nuevo: GET /notas/{id} - Obtener nota por ID con control de visibilidad
    @GetMapping("/{id}")
    public ResponseEntity<NotaDTO> obtenerNotaPorId(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String rolHeader) {
        try {
            Roles rol = null;
            if (rolHeader != null) rol = Roles.valueOf(rolHeader);

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

    // HU20 - GET /notas/docente/{id}
    @GetMapping("/docente/{id}")
    public ResponseEntity<?> obtenerNotasPorDocente(
            @PathVariable Integer id,
            @RequestParam(value = "materiaId", required = false) Long materiaId,
            @RequestParam(value = "grupoId", required = false) Long grupoId,
            @RequestHeader(value = "X-User-Role", required = true) String rolHeader) {
        try {
            Roles rol = Roles.valueOf(rolHeader);
            // Solo Docente y Administrador pueden acceder
            if (rol != Roles.Docente && rol != Roles.Administrador) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado");
            }

            List<NotaDTO> notas = notaServicio.obtenerNotasPorDocente(id, materiaId, grupoId);

            // Si el rol no puede ver comentarios, limpiarlos (aunque Docente/Admin sí pueden)
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
}
