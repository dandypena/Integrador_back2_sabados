package com.example.FrankySabado.mapper;

import com.example.FrankySabado.dtos.RendimientoBajoDTO;
import org.springframework.stereotype.Component;

@Component
public class NotaMapper {

    public RendimientoBajoDTO toRendimientoBajoDTO(Object[] data) {
        String nombreEstudiante = (String) data[0];
        String nombreMateria = String.valueOf(data[1]);
        Double promedio = (Double) data[2];

        String recomendacion = generarRecomendacion(nombreMateria, promedio);

        return new RendimientoBajoDTO(nombreEstudiante, nombreMateria, promedio, recomendacion);
    }

    private String generarRecomendacion(String materia, Double promedio) {
        if (promedio < 2.0) {
            return "Debe asistir a tutorías urgentes y revisar materiales de " + materia + ".";
        } else if (promedio < 2.5) {
            return "Se recomienda reforzar conocimientos en " + materia + " con ejercicios adicionales.";
        } else {
            return "Revisar apuntes y pedir asesoría en " + materia + " para mejorar el desempeño.";
        }
    }
}
