package com.example.FrankySabado.servicios;

import com.example.FrankySabado.ayudas.TipoEvaluacion;
import com.example.FrankySabado.dtos.*;
import com.example.FrankySabado.mapper.NotaMapper;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Materia;
import com.example.FrankySabado.modelos.Nota;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.repositorios.MateriaRepository;
import com.example.FrankySabado.repositorios.NotaRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotaServicio {

    private final NotaRepositorio notaRepositorio;
    private final EstudianteRepository estudianteRepositorio;
    private final MateriaRepository materiaRepository;
    private final NotaMapper notaMapper;

    public NotaServicio(NotaRepositorio notaRepositorio,
                       EstudianteRepository estudianteRepositorio,
                       MateriaRepository materiaRepository,
                       NotaMapper notaMapper) {
        this.notaRepositorio = notaRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
        this.materiaRepository = materiaRepository;
        this.notaMapper = notaMapper;
    }

    public NotaDTO registrarNota(NotaDTO notaDTO) {
        // Validaciones básicas
        if (notaDTO.getValor() < 0.0 || notaDTO.getValor() > 5.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor de la nota debe estar entre 0.0 y 5.0");
        }

        // Validar estudiante
        Estudiante estudiante = estudianteRepositorio.findById(notaDTO.getEstudianteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        // Materia es OPCIONAL ahora
        Materia materia = null;
        if (notaDTO.getMateriaId() != null) {
            materia = materiaRepository.findById(notaDTO.getMateriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada"));
        }

        Nota nota = notaMapper.dtoToEntity(notaDTO);
        nota.setEstudiante(estudiante);
        nota.setMateria(materia);  // Puede ser null
        nota.setNombreMateria(notaDTO.getNombreMateria()); // Guardar nombre de materia como texto
        nota.setFecha(LocalDate.now());

        Nota notaGuardada = notaRepositorio.save(nota);
        return notaMapper.entityToDto(notaGuardada);
    }

    public List<NotaDTO> obtenerTodasLasNotas() {
        return notaRepositorio.findAll().stream()
                .map(notaMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public NotaDTO actualizarNota(Integer id, NotaDTO notaDTO) {
        Nota notaExistente = notaRepositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada"));

        if (notaDTO.getValor() < 0.0 || notaDTO.getValor() > 5.0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor de la nota debe estar entre 0.0 y 5.0");
        }

        notaExistente.setValor(notaDTO.getValor());
        if (notaDTO.getComentario() != null) {
            notaExistente.setComentario(notaDTO.getComentario());
        }
        
        // Actualizar nombre de materia si se proporciona
        if (notaDTO.getNombreMateria() != null) {
            notaExistente.setNombreMateria(notaDTO.getNombreMateria());
        }
        
        // Actualizar materia si se proporciona un ID
        if (notaDTO.getMateriaId() != null) {
            Materia materia = materiaRepository.findById(notaDTO.getMateriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada"));
            notaExistente.setMateria(materia);
        }

        Nota notaActualizada = notaRepositorio.save(notaExistente);
        return notaMapper.entityToDto(notaActualizada);
    }

    public void eliminarNota(Integer id) {
        if (!notaRepositorio.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada");
        }
        notaRepositorio.deleteById(id);
    }

    public NotaDTO buscarNotaPorId(Integer id) {
        return notaRepositorio.findById(id)
                .map(notaMapper::entityToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada"));
    }

    // HU08 - Obtener notas por estudiante
    public List<NotaDTO> obtenerNotasPorEstudiante(Integer estudianteId) {
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        return estudiante.getNotas().stream()
                .map(notaMapper::entityToDto)
                .collect(Collectors.toList());
    }

    // HU14 - Obtener resumen académico de un estudiante
    public ResumenAcademicoDTO obtenerResumenAcademico(Integer estudianteId) {
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        List<Nota> notas = estudiante.getNotas();
        
        // Agrupar notas por materia
        Map<Materia, List<Nota>> notasPorMateria = notas.stream()
                .filter(n -> n.getMateria() != null)
                .collect(Collectors.groupingBy(Nota::getMateria));

        List<MateriaResumenDTO> materias = new ArrayList<>();
        double sumaPromedios = 0.0;

        for (Map.Entry<Materia, List<Nota>> entry : notasPorMateria.entrySet()) {
            Materia materia = entry.getKey();
            List<Nota> notasMateria = entry.getValue();

            double promedio = notasMateria.stream()
                    .mapToDouble(Nota::getValor)
                    .average()
                    .orElse(0.0);

            List<NotaDTO> notasDTO = notasMateria.stream()
                    .map(notaMapper::entityToDto)
                    .collect(Collectors.toList());

            materias.add(new MateriaResumenDTO(materia.getId(), materia.getNombre(), promedio, notasDTO));
            sumaPromedios += promedio;
        }

        double promedioGeneral = materias.isEmpty() ? 0.0 : sumaPromedios / materias.size();

        String nombreCompleto = estudiante.getUsuario() != null
                ? estudiante.getUsuario().getNombre()
                : "Sin nombre";

        return new ResumenAcademicoDTO(
                estudiante.getId(),
                nombreCompleto,
                promedioGeneral,
                materias
        );
    }

    // HU20 - Obtener notas por docente
    public List<NotaDTO> obtenerNotasPorDocente(Integer docenteId, Long materiaId, Long grupoId) {
        // Implementación básica - puede necesitar ajustes según la relación Docente-Materia-Grupo
        List<Nota> notas = notaRepositorio.findAll();

        if (materiaId != null) {
            notas = notas.stream()
                    .filter(n -> n.getMateria() != null && n.getMateria().getId().equals(materiaId.intValue()))
                    .collect(Collectors.toList());
        }

        return notas.stream()
                .map(notaMapper::entityToDto)
                .collect(Collectors.toList());
    }

    // Obtener estudiantes con bajo rendimiento
    public List<RendimientoBajoDTO> obtenerRendimientoBajo() {
        List<Estudiante> estudiantes = estudianteRepositorio.findAll();
        List<RendimientoBajoDTO> resultado = new ArrayList<>();

        for (Estudiante estudiante : estudiantes) {
            Map<TipoEvaluacion, List<Nota>> notasPorTipo = estudiante.getNotas().stream()
                    .collect(Collectors.groupingBy(Nota::getTipo));

            for (Map.Entry<TipoEvaluacion, List<Nota>> entry : notasPorTipo.entrySet()) {
                double promedio = entry.getValue().stream()
                        .mapToDouble(Nota::getValor)
                        .average()
                        .orElse(0.0);

                if (promedio < 3.0) {
                    String recomendacion = promedio < 2.0 
                            ? "Requiere atención urgente" 
                            : "Reforzar conocimientos";

                    String nombreCompleto = estudiante.getUsuario() != null
                            ? estudiante.getUsuario().getNombre()
                            : "Sin nombre";

                    resultado.add(new RendimientoBajoDTO(
                            nombreCompleto,
                            entry.getKey().name(),
                            promedio,
                            recomendacion
                    ));
                }
            }
        }

        return resultado;
    }

    // Registrar múltiples notas
    public void registrarMultiplesNotas(RegistrarNotasDTO dto) {
        Estudiante estudiante = estudianteRepositorio.findById(dto.getEstudianteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        for (RegistrarNotasDTO.NotaIndividualDTO notaDTO : dto.getNotas()) {
            if (notaDTO.getValor() < 0.0 || notaDTO.getValor() > 5.0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                        "El valor de la nota debe estar entre 0.0 y 5.0");
            }

            Nota nota = new Nota();
            nota.setValor(notaDTO.getValor());
            nota.setTipo(notaDTO.getTipo());
            nota.setEstudiante(estudiante);
            nota.setFecha(LocalDate.now());

            notaRepositorio.save(nota);
        }
    }

    // Consolidar notas por tipo de evaluación
    public List<String> consolidarNotasPorTipo(Integer estudianteId) {
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        Map<TipoEvaluacion, List<Nota>> notasPorTipo = estudiante.getNotas().stream()
                .collect(Collectors.groupingBy(Nota::getTipo));

        List<String> consolidado = new ArrayList<>();

        for (Map.Entry<TipoEvaluacion, List<Nota>> entry : notasPorTipo.entrySet()) {
            double promedio = entry.getValue().stream()
                    .mapToDouble(Nota::getValor)
                    .average()
                    .orElse(0.0);

            consolidado.add(String.format("%s: %.2f (Total: %d notas)", 
                    entry.getKey().name(), 
                    promedio, 
                    entry.getValue().size()));
        }

        return consolidado;
    }
}
