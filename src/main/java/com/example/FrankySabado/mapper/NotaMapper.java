package com.example.FrankySabado.mapper;

import com.example.FrankySabado.dtos.NotaDTO;
import com.example.FrankySabado.modelos.Nota;
import org.springframework.stereotype.Component;

@Component
public class NotaMapper {

    public NotaDTO entityToDto(Nota nota) {
        NotaDTO dto = new NotaDTO();
        dto.setId(nota.getId());
        dto.setValor(nota.getValor());
        dto.setComentario(nota.getComentario());
        dto.setEstudianteId(nota.getEstudiante().getId());
        dto.setTipo(nota.getTipo());
        dto.setFecha(nota.getFecha());
        return dto;
    }

    public Nota dtoToEntity(NotaDTO dto) {
        Nota nota = new Nota();
        nota.setId(dto.getId());
        nota.setValor(dto.getValor());
        nota.setComentario(dto.getComentario());
        nota.setTipo(dto.getTipo());
        nota.setFecha(dto.getFecha());
        return nota;
    }
}
