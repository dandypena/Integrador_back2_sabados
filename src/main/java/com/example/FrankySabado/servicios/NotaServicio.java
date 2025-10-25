package com.example.FrankySabado.servicios;

import com.example.FrankySabado.dtos.NotaDTO;
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
import java.util.List;
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

        // Validar estudiante y materia
        Estudiante estudiante = estudianteRepositorio.findById(notaDTO.getEstudianteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));

        Materia materia = materiaRepository.findById(notaDTO.getMateriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Materia no encontrada"));

        Nota nota = notaMapper.dtoToEntity(notaDTO);
        nota.setEstudiante(estudiante);
        nota.setMateria(materia);
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
}
