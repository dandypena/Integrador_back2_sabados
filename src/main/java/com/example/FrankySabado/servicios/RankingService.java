package com.example.FrankySabado.servicios;

import com.example.FrankySabado.dtos.RankingDTO;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private final EstudianteRepository estudianteRepository;

    public RankingService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<RankingDTO> obtenerRankingPorGrupo(Long grupoId) {
        List<Estudiante> estudiantes = estudianteRepository.findRankingByGrupo(grupoId);

        // Convertimos los estudiantes al DTO
        return estudiantes.stream()
                .map(e -> new RankingDTO(
                        e.getUsuario() != null ? e.getUsuario().getNombre() : "Sin nombre",
                        e.getGrupo() != null ? e.getGrupo().getNombre() : "Sin grupo",
                        e.getPromedio()
                ))
                .collect(Collectors.toList());
    }
}

