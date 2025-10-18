package com.example.FrankySabado.controlador;

import com.example.FrankySabado.dtos.RegistrarNotasDTO;
import com.example.FrankySabado.dtos.RendimientoBajoDTO;
import com.example.FrankySabado.servicios.NotaServicio;
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

}
