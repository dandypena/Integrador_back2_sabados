package com.example.FrankySabado.dtos;

import java.util.List;

public class MateriaResumenDTO {
    private Long materiaId;
    private String nombreMateria;
    private List<NotaDTO> ultimasNotas;

    public MateriaResumenDTO() {}

    public MateriaResumenDTO(Long materiaId, String nombreMateria, List<NotaDTO> ultimasNotas) {
        this.materiaId = materiaId;
        this.nombreMateria = nombreMateria;
        this.ultimasNotas = ultimasNotas;
    }

    public Long getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Long materiaId) {
        this.materiaId = materiaId;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public List<NotaDTO> getUltimasNotas() {
        return ultimasNotas;
    }

    public void setUltimasNotas(List<NotaDTO> ultimasNotas) {
        this.ultimasNotas = ultimasNotas;
    }
}

