package com.example.FrankySabado.dtos;

import java.util.List;

public class EstudianteNotasDTO {
    private Integer estudianteId;
    private String nombreEstudiante;
    private Double promedio;
    private List<NotaDTO> notas;

    // Constructores
    public EstudianteNotasDTO() {}

    public EstudianteNotasDTO(Integer estudianteId, String nombreEstudiante, Double promedio, List<NotaDTO> notas) {
        this.estudianteId = estudianteId;
        this.nombreEstudiante = nombreEstudiante;
        this.promedio = promedio;
        this.notas = notas;
    }

    // Getters y Setters
    public Integer getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Integer estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }
}

