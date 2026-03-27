package com.example.FrankySabado.controlador;

import com.example.FrankySabado.dtos.RankingDTO;
import com.example.FrankySabado.servicios.RankingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class RankingControlador {

    private final RankingService rankingService;

    public RankingControlador(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking/grupo/{id}")
    public List<RankingDTO> obtenerRankingPorGrupo(@PathVariable("id") Long grupoId) {
        return rankingService.obtenerRankingPorGrupo(grupoId);
    }
}

