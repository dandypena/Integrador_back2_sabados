package com.example.FrankySabado.servicios;

import com.example.FrankySabado.ayudas.Roles;
import com.example.FrankySabado.dtos.EstudianteNotasDTO;
import com.example.FrankySabado.dtos.EvolucionAcademicaDTO;
import com.example.FrankySabado.dtos.NotaDTO;
import com.example.FrankySabado.dtos.RegistrarNotasDTO;
import com.example.FrankySabado.dtos.RendimientoBajoDTO;
import com.example.FrankySabado.mapper.NotaMapper;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Nota;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.repositorios.FamiliarRepository;
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
    private final FamiliarRepository familiarRepository;
    private final NotaMapper notaMapper;

    public NotaServicio(NotaRepositorio notaRepositorio,
                        EstudianteRepository estudianteRepositorio,
                        FamiliarRepository familiarRepository,
                        NotaMapper notaMapper) {
        this.notaRepositorio = notaRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
        this.familiarRepository = familiarRepository;
        this.notaMapper = notaMapper;
    }

    // 🟢 Registrar múltiples notas con validación de límite por tipo
    public void registrarMultiplesNotas(RegistrarNotasDTO dto) {
        Estudiante estudiante = estudianteRepositorio.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<Nota> notas = dto.getNotas().stream().map(n -> {

            long countPorTipo = notaRepositorio.countByEstudianteIdAndTipo(
                    estudiante.getId(),
                    n.getTipo()
            );


            if (countPorTipo >= 3) {
                throw new RuntimeException("Límite de notas del tipo " + n.getTipo() + " alcanzado");
            }


            Nota nota = new Nota();
            nota.setEstudiante(estudiante);
            nota.setTipo(n.getTipo());
            nota.setValor(n.getValor());
            nota.setFecha(LocalDate.now());
            return nota;
        }).collect(Collectors.toList());

        notaRepositorio.saveAll(notas);
    }


    public List<String> consolidarNotasPorTipo(Integer idEstudiante) {
        List<Object[]> resultados = notaRepositorio.obtenerConsolidadoPorTipo(idEstudiante);
        return resultados.stream()
                .map(r -> "Tipo: " + r[0] + " | Promedio: " + r[1])
                .collect(Collectors.toList());
    }

    // Método para obtener notas de bajo rendimiento (mapea usando NotaMapper)
    public List<RendimientoBajoDTO> obtenerRendimientoBajo() {
        List<Object[]> resultados = notaRepositorio.findRendimientoBajo();
        return resultados.stream()
                .map(notaMapper::toRendimientoBajoDTO)
                .collect(Collectors.toList());
    }

    // 🟢 HU08 - Registrar una nota individual
    public NotaDTO registrarNota(NotaDTO notaDTO) {
        Estudiante estudiante = estudianteRepositorio.findById(notaDTO.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validar límite de 3 notas por tipo
        long countPorTipo = notaRepositorio.countByEstudianteIdAndTipo(
                estudiante.getId(),
                notaDTO.getTipo()
        );

        if (countPorTipo >= 3) {
            throw new RuntimeException("Límite de notas del tipo " + notaDTO.getTipo() + " alcanzado");
        }

        Nota nota = new Nota();
        nota.setEstudiante(estudiante);
        nota.setTipo(notaDTO.getTipo());
        nota.setValor(notaDTO.getValor());
        nota.setFecha(notaDTO.getFecha() != null ? notaDTO.getFecha() : LocalDate.now());

        Nota notaGuardada = notaRepositorio.save(nota);

        return convertirANotaDTO(notaGuardada);
    }

    // 🟢 HU08 - Obtener notas de un estudiante
	public List<NotaDTO> obtenerNotasPorEstudiante(Integer estudianteId) {
		Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
				.orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

		List<Nota> notas = notaRepositorio.findByEstudianteId(estudianteId);

		return notas.stream()
				.map(this::convertirANotaDTO)
				.collect(Collectors.toList());
	}
    // 🟢 HU08 - Actualizar una nota
    public NotaDTO actualizarNota(Integer id, NotaDTO notaDTO) {
        Nota nota = notaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        // Actualizar campos
        if (notaDTO.getValor() != null) {
            nota.setValor(notaDTO.getValor());
        }
        if (notaDTO.getTipo() != null) {
            nota.setTipo(notaDTO.getTipo());
        }
        if (notaDTO.getFecha() != null) {
            nota.setFecha(notaDTO.getFecha());
        }

        Nota notaActualizada = notaRepositorio.save(nota);
        return convertirANotaDTO(notaActualizada);
    }

    // 🟢 HU08 - Eliminar una nota
    public void eliminarNota(Integer id) {
        if (!notaRepositorio.existsById(id)) {
            throw new RuntimeException("Nota no encontrada");
        }
        notaRepositorio.deleteById(id);
    }

    // 🟢 HU10 - Obtener notas por materia y grupo (solo docentes/admins)
    public List<EstudianteNotasDTO> obtenerNotasPorMateriaYGrupo(Long materiaId, Long grupoId, Roles rolUsuario) {
        // Validar que solo docentes y administradores puedan acceder
        if (rolUsuario != Roles.Docente && rolUsuario != Roles.Administrador) {
            throw new RuntimeException("Acceso denegado: Solo docentes y administradores pueden acceder a este recurso");
        }

        // Obtener todas las notas filtradas por materia y grupo
        List<Nota> notas = notaRepositorio.findByMateriaIdAndGrupoId(materiaId, grupoId);

        // Agrupar notas por estudiante
        Map<Integer, List<Nota>> notasPorEstudiante = new HashMap<>();
        for (Nota nota : notas) {
            Integer estudianteId = nota.getEstudiante().getId();
            notasPorEstudiante.computeIfAbsent(estudianteId, k -> new ArrayList<>()).add(nota);
        }

        // Construir DTOs
        List<EstudianteNotasDTO> resultado = new ArrayList<>();
        for (Map.Entry<Integer, List<Nota>> entry : notasPorEstudiante.entrySet()) {
            List<Nota> notasEstudiante = entry.getValue();
            if (!notasEstudiante.isEmpty()) {
                Estudiante estudiante = notasEstudiante.get(0).getEstudiante();

                List<NotaDTO> notasDTO = notasEstudiante.stream()
                        .map(this::convertirANotaDTO)
                        .collect(Collectors.toList());

                EstudianteNotasDTO dto = new EstudianteNotasDTO();
                dto.setEstudianteId(estudiante.getId());
                dto.setNombreEstudiante(estudiante.getUsuario().getNombre());
                dto.setPromedio(estudiante.getPromedio());
                dto.setNotas(notasDTO);

                resultado.add(dto);
            }
        }

        return resultado;
    }

    // 🟢 HU15 - Obtener notas desde módulo familiar (reutiliza servicio de consulta por estudiante)
    public List<NotaDTO> obtenerNotasParaFamiliar(Integer estudianteId, Integer familiarUsuarioId, Roles rolUsuario) {
        // Validar que el usuario tenga rol FAMILIAR
        if (rolUsuario != Roles.Familiar) {
            throw new RuntimeException("Acceso denegado: Solo usuarios con rol Familiar pueden acceder a este recurso");
        }

        // Verificar que el familiar esté vinculado al estudiante
        boolean esVinculado = familiarRepository.existsByUsuarioIdAndEstudianteVinculadoId(familiarUsuarioId, estudianteId);
        if (!esVinculado) {
            throw new RuntimeException("Acceso denegado: No tiene autorización para ver las notas de este estudiante");
        }

        // Reutilizar el servicio de consulta por estudiante
        return obtenerNotasPorEstudiante(estudianteId);
    }

    // 🟢 HU18 - Consultar evolución académica por periodos
    public List<EvolucionAcademicaDTO> obtenerEvolucionAcademica(Integer estudianteId) {
        // Verificar que el estudiante existe
        Estudiante estudiante = estudianteRepositorio.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Obtener todas las notas del estudiante ordenadas por fecha
        List<Nota> notas = notaRepositorio.findByEstudianteIdOrderByFechaAsc(estudianteId);

        // Agrupar notas por periodo (año-semestre)
        Map<String, List<Nota>> notasPorPeriodo = new HashMap<>();
        for (Nota nota : notas) {
            if (nota.getFecha() != null) {
                String periodo = calcularPeriodo(nota.getFecha());
                notasPorPeriodo.computeIfAbsent(periodo, k -> new ArrayList<>()).add(nota);
            }
        }

        // Construir DTOs de evolución académica
        List<EvolucionAcademicaDTO> evolucion = new ArrayList<>();
        for (Map.Entry<String, List<Nota>> entry : notasPorPeriodo.entrySet()) {
            String periodoKey = entry.getKey();
            List<Nota> notasPeriodo = entry.getValue();

            // Calcular año y semestre del periodo
            String[] parts = periodoKey.split("-");
            Integer año = Integer.parseInt(parts[0]);
            Integer semestre = Integer.parseInt(parts[1]);

            // Calcular promedio del periodo
            double promedio = notasPeriodo.stream()
                    .mapToDouble(Nota::getValor)
                    .average()
                    .orElse(0.0);

            // Convertir notas a DTO
            List<NotaDTO> notasDTO = notasPeriodo.stream()
                    .map(this::convertirANotaDTO)
                    .collect(Collectors.toList());

            EvolucionAcademicaDTO dto = new EvolucionAcademicaDTO();
            dto.setPeriodo(periodoKey);
            dto.setAño(año);
            dto.setSemestre(semestre);
            dto.setPromedioGeneral(Math.round(promedio * 100.0) / 100.0);
            dto.setCantidadNotas(notasPeriodo.size());
            dto.setNotas(notasDTO);

            evolucion.add(dto);
        }

        // Ordenar por periodo (año-semestre) ascendente
        evolucion.sort((e1, e2) -> {
            int compareAño = e1.getAño().compareTo(e2.getAño());
            if (compareAño != 0) return compareAño;
            return e1.getSemestre().compareTo(e2.getSemestre());
        });

        return evolucion;
    }

    // 🔹 Método auxiliar para calcular el periodo basado en la fecha
    private String calcularPeriodo(LocalDate fecha) {
        int año = fecha.getYear();
        int mes = fecha.getMonthValue();

        // Semestre 1: Enero - Junio (meses 1-6)
        // Semestre 2: Julio - Diciembre (meses 7-12)
        int semestre = (mes <= 6) ? 1 : 2;

        return año + "-" + semestre;
    }

    // 🔹 Método auxiliar para convertir Nota a NotaDTO
    private NotaDTO convertirANotaDTO(Nota nota) {
        NotaDTO dto = new NotaDTO();
        dto.setId(nota.getId());
        dto.setValor(nota.getValor());
        dto.setTipo(nota.getTipo());
        dto.setFecha(nota.getFecha());
        dto.setEstudianteId(nota.getEstudiante().getId());
        return dto;
    }

	public void guardarNota(Nota nota) {
		if (nota.getValor() < 0.0 || nota.getValor() > 5.0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El valor de la nota debe estar entre 0.0 y 5.0");
		}

		notaRepositorio.save(nota);
	}
}


