package com.example.FrankySabado.servicios;

import com.example.FrankySabado.dtos.RegistrarNotasDTO;
import com.example.FrankySabado.dtos.RendimientoBajoDTO;
import com.example.FrankySabado.mapper.NotaMapper;
import com.example.FrankySabado.modelos.Estudiante;
import com.example.FrankySabado.modelos.Nota;
import com.example.FrankySabado.repositorios.EstudianteRepository;
import com.example.FrankySabado.repositorios.NotaRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotaServicio {

    private final NotaRepositorio notaRepositorio;
    private final EstudianteRepository estudianteRepositorio;
    private final NotaMapper notaMapper;

    public NotaServicio(NotaRepositorio notaRepositorio,
                        EstudianteRepository estudianteRepositorio,
                        NotaMapper notaMapper) {
        this.notaRepositorio = notaRepositorio;
        this.estudianteRepositorio = estudianteRepositorio;
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
}
