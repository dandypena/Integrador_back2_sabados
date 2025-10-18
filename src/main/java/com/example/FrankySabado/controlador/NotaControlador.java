package com.example.FrankySabado.controlador;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.EstudianteNotasDTO;
import com.example.FrankySabado.dtos.EvolucionAcademicaDTO;
import com.example.FrankySabado.dtos.NotaDTO;
import com.example.FrankySabado.dtos.RegistrarNotasDTO;
import com.example.FrankySabado.dtos.RendimientoBajoDTO;
import com.example.FrankySabado.servicios.NotaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotaControlador {

    private final NotaServicio notaServicio;

    public NotaControlador(NotaServicio notaServicio) {
        this.notaServicio = notaServicio;
    }

    // 🟢 HU08 - POST /notas - Registrar una nota individual
    @PostMapping
    public ResponseEntity<NotaDTO> registrarNota(@RequestBody NotaDTO notaDTO) {
        try {
            NotaDTO notaCreada = notaServicio.registrarNota(notaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(notaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // 🟢 HU08 - GET /notas/estudiante/{id} - Listar notas por estudiante
    @GetMapping("/estudiante/{id}")
    public ResponseEntity<List<NotaDTO>> obtenerNotasPorEstudiante(@PathVariable Integer id) {
        try {
            List<NotaDTO> notas = notaServicio.obtenerNotasPorEstudiante(id);
            return ResponseEntity.ok(notas);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 🟢 HU08 - PUT /notas/{id} - Actualizar una nota
    @PutMapping("/{id}")
    public ResponseEntity<NotaDTO> actualizarNota(@PathVariable Integer id, @RequestBody NotaDTO notaDTO) {
        try {
            NotaDTO notaActualizada = notaServicio.actualizarNota(id, notaDTO);
            return ResponseEntity.ok(notaActualizada);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nota no encontrada");
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
    public ResponseEntity<List<EvolucionAcademicaDTO>> obtenerEvolucionAcademica(@PathVariable Integer id) {
        try {
            List<EvolucionAcademicaDTO> evolucion = notaServicio.obtenerEvolucionAcademica(id);
            return ResponseEntity.ok(evolucion);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
