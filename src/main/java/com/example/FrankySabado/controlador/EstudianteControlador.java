package com.example.FrankySabado.controlador;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.EstudianteListaDTO;
import com.example.FrankySabado.dtos.MateriaResumenDTO;
import com.example.FrankySabado.dtos.ResumenAcademicoDTO;
import com.example.FrankySabado.dtos.NotaDTO;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.servicios.NotaServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteControlador {

    private final NotaServicio notaServicio;
    private final EstudianteRepository estudianteRepository;
    private static final Set<Roles> VIEW_COMMENT_ROLES = Set.of(Roles.Docente, Roles.Administrador, Roles.Familiar);

    public EstudianteControlador(NotaServicio notaServicio, EstudianteRepository estudianteRepository) {
        this.notaServicio = notaServicio;
        this.estudianteRepository = estudianteRepository;
    }

    // GET /estudiantes - Listar todos los estudiantes
    @GetMapping
    public ResponseEntity<List<EstudianteListaDTO>> listarEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteRepository.findAll();
            
            List<EstudianteListaDTO> estudiantesDTO = estudiantes.stream()
                .map(e -> new EstudianteListaDTO(
                    e.getId(),
                    e.getUsuario().getNombre(),
                    e.getUsuario().getCorreo(),
                    e.getFechaNacimiento(),
                    e.getPromedio(),
                    e.getUsuario().getId(),
                    e.getGrupo() != null ? e.getGrupo().getNombre() : "Sin grupo"
                ))
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(estudiantesDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // HU14 - GET /estudiantes/{id}/resumen-academico
    @GetMapping("/{id}/resumen-academico")
    public ResponseEntity<ResumenAcademicoDTO> obtenerResumenAcademico(
            @PathVariable Integer id,
            @RequestHeader(value = "X-User-Role", required = false) String rolHeader) {
        try {
            Roles rol = null;
            if (rolHeader != null) rol = Roles.valueOf(rolHeader);

            ResumenAcademicoDTO resumen = notaServicio.obtenerResumenAcademico(id);

            // Si el rol no puede ver comentarios, limpiar comentarios dentro de cada nota
            if (rol == null || !VIEW_COMMENT_ROLES.contains(rol)) {
                if (resumen.getMaterias() != null) {
                    for (MateriaResumenDTO m : resumen.getMaterias()) {
                        if (m.getUltimasNotas() != null) {
                            for (NotaDTO n : m.getUltimasNotas()) {
                                n.setComentario(null);
                            }
                        }
                    }
                }
            }

            return ResponseEntity.ok(resumen);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

